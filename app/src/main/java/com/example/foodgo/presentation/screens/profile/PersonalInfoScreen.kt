package com.example.foodgo.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.foodgo.presentation.viewmodel.ProfileViewModel
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.foodgo.R
import com.example.foodgo.ui.theme.GreyLight
import com.example.foodgo.ui.theme.IconGrey3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalInfoScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onChangePasswordClick: () -> Unit
) {
    val state = viewModel.profileState.collectAsState()
    val isSaving = viewModel.isSaving.collectAsState()
    val saveSuccess = viewModel.saveSuccess.collectAsState()
    val error = viewModel.errorMessage.collectAsState()

    val showPasswordDialog = viewModel.showPasswordDialog.collectAsState()
    val oldPassword = viewModel.oldPassword.collectAsState()
    val newPassword = viewModel.newPassword.collectAsState()
    val confirmPassword = viewModel.confirmPassword.collectAsState()
    val passwordError = viewModel.passwordChangeError.collectAsState()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 50.dp, bottom = 29.dp, start = 24.dp, end = 24.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .background(GreyLight, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = IconGrey3,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Личная информация",
                color = Color.Black,
                fontSize = 17.sp,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Имя
        OutlinedTextField(
            value = state.value.username,
            onValueChange = { viewModel.onNameChanged(it) },
            label = { Text("Имя") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Описание
        OutlinedTextField(
            value = state.value.description,
            onValueChange = { viewModel.onDescriptionChanged(it) },
            label = { Text("Описание профиля") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Почта
        OutlinedTextField(
            value = state.value.email,
            onValueChange = {},
            label = { Text("Почта") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Сменить пароль
        TextButton(onClick = { viewModel.onChangePasswordClick() }) {
            Text("Сменить пароль")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Сохранить
        Button(
            onClick = { viewModel.updateProfile() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Сохранить изменения")
        }

        if (isSaving.value) {
            Text("Сохранение...", color = Color.Gray)
        }

        saveSuccess.value?.let {
            if (it) {
                Text("Изменения сохранены", color = Color.Green)
            } else {
                Text("Ошибка сохранения: ${error.value ?: "неизвестная"}", color = Color.Red)
            }
        }

    }
    if (showPasswordDialog.value) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { viewModel.onPasswordDialogDismiss() },
            title = { Text("Сменить пароль") },
            text = {
                Column {
                    OutlinedTextField(
                        value = oldPassword.value,
                        onValueChange = { viewModel.oldPassword.value = it },
                        label = { Text("Старый пароль") }
                    )
                    OutlinedTextField(
                        value = newPassword.value,
                        onValueChange = { viewModel.newPassword.value = it },
                        label = { Text("Новый пароль") }
                    )
                    OutlinedTextField(
                        value = confirmPassword.value,
                        onValueChange = { viewModel.confirmPassword.value = it },
                        label = { Text("Повторите новый пароль") }
                    )
                    passwordError.value?.let {
                        Text(it, color = Color.Red, fontSize = 12.sp)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.changePassword() }) {
                    Text("Сменить")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.onPasswordDialogDismiss() }) {
                    Text("Отмена")
                }
            }
        )
    }


}

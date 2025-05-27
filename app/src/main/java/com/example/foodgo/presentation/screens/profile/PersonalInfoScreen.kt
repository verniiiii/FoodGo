package com.example.foodgo.presentation.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodgo.R
import com.example.foodgo.presentation.components.ScreenHeader
import com.example.foodgo.presentation.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalInfoScreen(
    onBack: () -> Unit = {},
    viewModel: ProfileViewModel = hiltViewModel(),
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

    ScreenHeader(stringResource(R.string.personal_info), onBackClick = onBack) {
        OutlinedTextField(
            value = state.value.username,
            onValueChange = { viewModel.onNameChanged(it) },
            label = { Text(stringResource(R.string.name)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = state.value.description,
            onValueChange = { viewModel.onDescriptionChanged(it) },
            label = { Text(stringResource(R.string.description_profile)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = state.value.email,
            onValueChange = {},
            label = { Text(stringResource(R.string.email)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { viewModel.onChangePasswordClick() }) {
            Text(stringResource(R.string.change_password))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.updateProfile() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.save))
        }

        if (isSaving.value) {
            Text(stringResource(R.string.save_process), color = Color.Gray)
        }

        saveSuccess.value?.let {
            if (it) {
                Text(stringResource(R.string.changed_save), color = Color.Green)
            } else {
                Text(
                    stringResource(
                        R.string.error_save,
                        error.value ?: stringResource(R.string.unknown_error)
                    ), color = Color.Red)
            }
        }
    }
    if (showPasswordDialog.value) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { viewModel.onPasswordDialogDismiss() },
            title = { Text(stringResource(R.string.change_password)) },
            text = {
                Column {
                    OutlinedTextField(
                        value = oldPassword.value,
                        onValueChange = { viewModel.oldPassword.value = it },
                        label = { Text(stringResource(R.string.old_password)) }
                    )
                    OutlinedTextField(
                        value = newPassword.value,
                        onValueChange = { viewModel.newPassword.value = it },
                        label = { Text(stringResource(R.string.new_password)) }
                    )
                    OutlinedTextField(
                        value = confirmPassword.value,
                        onValueChange = { viewModel.confirmPassword.value = it },
                        label = { Text(stringResource(R.string.repeat_password2)) }
                    )
                    passwordError.value?.let {
                        Text(it, color = Color.Red, fontSize = 12.sp)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.changePassword() }) {
                    Text(stringResource(R.string.changed))
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.onPasswordDialogDismiss() }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

package com.example.foodgo.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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

    val textFieldModifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)
        .height(62.dp)

    ScreenHeader(stringResource(R.string.personal_info), onBackClick = onBack) {
        // Имя
        Text(
            text = stringResource(R.string.name),
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
        TextField(
            value = state.value.username,
            onValueChange = { viewModel.onNameChanged(it) },
            placeholder = {
                Text(
                    stringResource(R.string.name_example),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.surface
                )
            },
            modifier = textFieldModifier,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                containerColor = MaterialTheme.colorScheme.background
            ),
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Описание
        Text(
            text = stringResource(R.string.description_profile),
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
        TextField(
            value = state.value.description,
            onValueChange = { viewModel.onDescriptionChanged(it) },
            placeholder = {
                Text(
                    stringResource(R.string.description_profile),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.surface
                )
            },
            modifier = textFieldModifier,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                containerColor = MaterialTheme.colorScheme.background
            ),
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email (недоступный для редактирования)
        Text(
            text = stringResource(R.string.email),
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
        TextField(
            value = state.value.email,
            onValueChange = {},
            placeholder = {
                Text(
                    stringResource(R.string.email),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.surface
                )
            },
            enabled = false,
            modifier = textFieldModifier,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledBorderColor = Color.Transparent,
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                // = MaterialTheme.colorScheme.background
            ),
            shape = RoundedCornerShape(10.dp)
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
            Text(stringResource(R.string.save_process))
        }

        saveSuccess.value?.let {
            if (it) {
                Text(stringResource(R.string.changed_save))
            } else {
                Text(
                    stringResource(
                        R.string.error_save,
                        error.value ?: stringResource(R.string.unknown_error)
                    ),
                    color = Color.Red
                )
            }
        }
    }

    // Диалог смены пароля
    if (showPasswordDialog.value) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { viewModel.onPasswordDialogDismiss() },
            title = { Text(stringResource(R.string.change_password)) },
            text = {
                Column{
                    // Старый пароль
                    Text(
                        text = stringResource(R.string.old_password),
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    TextField(
                        value = oldPassword.value,
                        onValueChange = { viewModel.oldPassword.value = it },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.password_example),
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.surface
                            )
                        },
                        modifier = textFieldModifier,
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Новый пароль
                    Text(
                        text = stringResource(R.string.new_password),
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    TextField(
                        value = newPassword.value,
                        onValueChange = { viewModel.newPassword.value = it },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.password_example),
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.surface
                            )
                        },
                        modifier = textFieldModifier,
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Повтор пароля
                    Text(
                        text = stringResource(R.string.repeat_password2),
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    TextField(
                        value = confirmPassword.value,
                        onValueChange = { viewModel.confirmPassword.value = it },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.password_example),
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.surface
                            )
                        },
                        modifier = textFieldModifier,
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    passwordError.value?.let {
                        Spacer(modifier = Modifier.height(4.dp))
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


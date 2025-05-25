package com.example.foodgo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodgo.PreferencesManager
import com.example.foodgo.data.remote.client.ApiClient.userApi
import com.example.foodgo.data.remote.dto.ChangePasswordRequest
import com.example.foodgo.data.remote.dto.UserUpdateDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val isSaving = MutableStateFlow(false)
    val saveSuccess = MutableStateFlow<Boolean?>(null)
    val errorMessage = MutableStateFlow<String?>(null)


    // Состояние профиля пользователя
    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> = _profileState

    init {
        loadUserProfile()
    }

    // Состояния смены пароля
    val showPasswordDialog = MutableStateFlow(false)
    val oldPassword = MutableStateFlow("")
    val newPassword = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")
    val passwordChangeSuccess = MutableStateFlow<Boolean?>(null)
    val passwordChangeError = MutableStateFlow<String?>(null)

    fun onChangePasswordClick() {
        showPasswordDialog.value = true
    }

    fun onPasswordDialogDismiss() {
        showPasswordDialog.value = false
        oldPassword.value = ""
        newPassword.value = ""
        confirmPassword.value = ""
        passwordChangeSuccess.value = null
        passwordChangeError.value = null
    }

    fun changePassword() {
        viewModelScope.launch {
            val token = preferencesManager.getUserToken() ?: return@launch

            if (newPassword.value != confirmPassword.value) {
                passwordChangeError.value = "Новые пароли не совпадают"
                return@launch
            }

            try {
                val response = userApi.changePassword(
                    token = "Bearer $token",
                    ChangePasswordRequest(
                        oldPassword = oldPassword.value,
                        newPassword = newPassword.value
                    )
                )

                if (response.isSuccessful) {
                    passwordChangeSuccess.value = true
                    passwordChangeError.value = null
                    onPasswordDialogDismiss()

                } else {
                    println(response.code().toString())
                    val errorMsg = response.errorBody()?.string() ?: "Ошибка при смене пароля"
                    passwordChangeError.value = errorMsg
                    passwordChangeSuccess.value = false
                }
            } catch (e: Exception) {
                passwordChangeError.value = "Ошибка сети: ${e.localizedMessage}"
                passwordChangeSuccess.value = false
            }
        }
    }


    private fun loadUserProfile() {
        viewModelScope.launch {
            // Загружаем данные пользователя из PreferencesManager
            val username = preferencesManager.getUsername() ?: "Гость"
            val description = preferencesManager.getDescription() ?: ""
            val email = preferencesManager.getLogin() ?: ""

            _profileState.value = _profileState.value.copy(
                username = username,
                description = description,
                email = email
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            preferencesManager.clearUserSession()
            preferencesManager.setUserLoggedIn(false)
            // Можно добавить навигацию на экран авторизации
        }
    }

    fun updateProfile() {
        viewModelScope.launch {
            isSaving.value = true
            saveSuccess.value = null
            errorMessage.value = null

            val token = preferencesManager.getUserToken() ?: return@launch

            val updated = UserUpdateDTO(
                username = _profileState.value.username,
                profileDescription = _profileState.value.description
            )

            try {
                val response = userApi.updateUser("Bearer $token", updated)

                if (response.isSuccessful) {
                    preferencesManager.saveUserData(
                        id = 0,
                        username = _profileState.value.username,
                        login = "",
                        description = _profileState.value.description
                    )
                    loadUserProfile()
                    saveSuccess.value = true
                } else {
                    errorMessage.value = "Ошибка при сохранении"
                    saveSuccess.value = false
                }
            } catch (e: Exception) {
                errorMessage.value = "Ошибка сети"
                saveSuccess.value = false
            } finally {
                isSaving.value = false
            }
        }
    }




    fun onNameChanged(newName: String) {
        _profileState.update { it.copy(username = newName) }
    }

    fun onDescriptionChanged(newDescription: String) {
        _profileState.update { it.copy(description = newDescription) }
    }





    fun onChangePhotoClick() {
        // Вызов UI-события, например: открыть галерею
        // Это можно сделать через EventBus/Callback/State с флагом
    }

    // Состояние профиля
    data class ProfileState(
        val username: String = "",
        val description: String = "",
        val email: String = ""
    )

    data class UserProfile(
        val name: String,
        val description: String,
        val email: String,
    )


}
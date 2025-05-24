package com.example.foodgo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodgo.PreferencesManager
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

    // Состояние профиля пользователя
    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> = _profileState

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            // Загружаем данные пользователя из PreferencesManager
            val username = preferencesManager.getUsername() ?: "Гость"
            val description = preferencesManager.getDescription() ?: ""
            val phone = preferencesManager.getPhone() ?: ""

            _profileState.value = _profileState.value.copy(
                username = username,
                description = description,
                phone = phone,
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

    fun updateProfile(username: String, description: String) {
        viewModelScope.launch {
            preferencesManager.saveUserData(
                id = 0, // ID должен быть получен из реальных данных пользователя
                username = username,
                login = "", // Логин должен быть получен из реальных данных
                description = description,
                phone = _profileState.value.phone,

            )
            loadUserProfile() // Обновляем состояние после сохранения
        }
    }



    fun onNameChanged(newName: String) {
        _profileState.update { it.copy(username = newName) }
    }

    fun onDescriptionChanged(newDescription: String) {
        _profileState.update { it.copy(description = newDescription) }
    }

    fun onChangePhotoUrl(newUrl: String) {
        _profileState.update { it.copy(photoUrl = newUrl) }
    }

    fun onSave() {
        viewModelScope.launch {
            val updated = UserProfile(
                name = _profileState.value.username,
                description = _profileState.value.description,
                email = _profileState.value.email,
                photoUrl = _profileState.value.photoUrl
            )
            //profileRepository.updateProfile(updated)
        }
    }

    fun onChangePhotoClick() {
        // Вызов UI-события, например: открыть галерею
        // Это можно сделать через EventBus/Callback/State с флагом
    }

    // Состояние профиля
    data class ProfileState(
        val username: String = "",
        val description: String = "",
        val email: String = "",
        val phone: String = "",
        val photoUrl: String = ""
    )

    data class UserProfile(
        val name: String,
        val description: String,
        val email: String,
        val photoUrl: String
    )


}
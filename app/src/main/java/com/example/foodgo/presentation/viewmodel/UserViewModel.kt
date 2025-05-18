package com.example.foodgo.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodgo.PreferencesManager
import com.example.foodgo.data.remote.api.UserApi
import com.example.foodgo.data.remote.dto.UserAddressDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val userApi: UserApi
) : ViewModel() {

    private val _userName = MutableStateFlow<String?>("") // имя пользователя
    val userName: StateFlow<String?> = _userName

    private val _userAddresses = MutableStateFlow<List<UserAddressDTO>>(emptyList()) // адреса
    val userAddresses: StateFlow<List<UserAddressDTO>> = _userAddresses

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val token = preferencesManager.getUserToken()
            Log.d("UserViewModel", "Получен токен: $token")

            if (!token.isNullOrEmpty()) {
                try {
                    val response = userApi.getUserByToken("Bearer $token")
                    Log.d("UserViewModel", "Ответ от API: $response")

                    if (response.isSuccessful) {
                        val userWithAddresses = response.body()
                        Log.d("UserViewModel", "Полученные данные пользователя и адресов: $userWithAddresses")

                        if (userWithAddresses != null) {
                            // Сохраняем данные пользователя
                            val user = userWithAddresses.user
                            preferencesManager.saveUserData(
                                id = user.id!!,
                                username = user.username,
                                login = user.login,
                                description = user.profileDescription,
                                phone = user.phoneNumber,
                                notificationsEnabled = user.notificationsEnabled
                            )
                            _userName.value = user.username

                            // Сохраняем адреса
                            _userAddresses.value = userWithAddresses.addresses

                            Log.d("UserViewModel", "Имя пользователя и адреса сохранены")
                        }
                    } else {
                        Log.e("UserViewModel", "Ошибка ответа API: код ${response.code()}, сообщение: ${response.message()}")
                        Log.e("UserViewModel", "Ошибка тела ответа: ${response.errorBody()?.string()}")

                        _userName.value = "Гость"
                        _userAddresses.value = emptyList()
                    }
                } catch (e: Exception) {
                    Log.e("UserViewModel", "Ошибка при вызове API: ${e.message}", e)
                    _userName.value = "Гость"
                    _userAddresses.value = emptyList()
                }
            } else {
                Log.w("UserViewModel", "Токен отсутствует, устанавливаем имя 'Гость'")
                _userName.value = "Гость"
                _userAddresses.value = emptyList()
            }
        }
    }
}

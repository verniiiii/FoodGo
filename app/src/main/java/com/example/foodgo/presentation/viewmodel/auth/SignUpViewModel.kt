package com.example.foodgo.presentation.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodgo.data.remote.api.AuthApi
import com.example.foodgo.data.remote.dto.user.AuthResponse
import com.example.foodgo.data.remote.dto.user.RegisterRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authApi: AuthApi
) : ViewModel() {

    // Состояния для UI
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _registrationResult = MutableStateFlow<Result<AuthResponse>?>(null)
    val registrationResult: StateFlow<Result<AuthResponse>?> = _registrationResult

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val registerRequest = RegisterRequest(
                    username = name,
                    password = password,
                    login = email
                )
                val response: Response<AuthResponse> = withContext(Dispatchers.IO) {
                    authApi.register(registerRequest)
                }

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _registrationResult.value = Result.success(body)
                    } else {
                        _registrationResult.value = Result.failure(Exception("Пустой ответ от сервера"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()?.takeIf { it.isNotBlank() } ?: "Неизвестная ошибка"
                    _registrationResult.value = Result.failure(Exception("Ошибка ${response.code()}: $errorBody"))
                }
            } catch (e: Exception) {
                _registrationResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetRegistrationResult() {
        _registrationResult.value = null
    }
}

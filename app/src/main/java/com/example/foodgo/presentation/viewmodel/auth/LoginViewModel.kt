package com.example.foodgo.presentation.viewmodel.auth

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodgo.PreferencesManager
import com.example.foodgo.R
import com.example.foodgo.data.remote.api.AuthApi
import com.example.foodgo.data.remote.dto.user.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authApi: AuthApi,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginResult>(LoginResult.Idle)
    val loginState: StateFlow<LoginResult> = _loginState

    fun onLoginClick(email: String, password: String, rememberMe: Boolean, fillFields: String, errorLodIn: String, errorNetwork: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = LoginResult.Error(fillFields)
            return
        }

        viewModelScope.launch {
            _loginState.value = LoginResult.Loading
            try {
                val response = authApi.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    val token = response.body()?.token ?: ""
                    if (rememberMe) {
                        preferencesManager.setUserLoggedIn(true)
                        preferencesManager.setUserToken(token)
                    }
                    preferencesManager.setRememberMeEnabled(rememberMe)
                    preferencesManager.setUserToken(token)
                    _loginState.value = LoginResult.Success
                } else {
                    _loginState.value = LoginResult.Error(
                        errorLodIn.format(response.code())
                    )
                }
            } catch (e: Exception) {
                _loginState.value = LoginResult.Error(
                    errorNetwork.format(e.message)
                )
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = LoginResult.Idle
    }

    sealed class LoginResult {
        object Idle : LoginResult()
        object Success : LoginResult()
        object Loading : LoginResult()
        data class Error(val message: String) : LoginResult()
    }
}




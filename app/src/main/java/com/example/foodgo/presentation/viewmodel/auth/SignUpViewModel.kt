package com.example.foodgo.presentation.viewmodel.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodgo.R
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
    private val _registrationResult = MutableStateFlow<Result<AuthResponse>?>(null)
    val registrationResult: StateFlow<Result<AuthResponse>?> = _registrationResult

    fun register(name: String, email: String, password: String, context: Context) {
        viewModelScope.launch {
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
                        _registrationResult.value = Result.failure(Exception(context.getString(R.string.empty_answer_server)))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()?.takeIf { it.isNotBlank() } ?: context.getString(R.string.unknown_error)
                    _registrationResult.value = Result.failure(Exception(
                        context.getString(
                            R.string.error_bode,
                            response.code().toString(),
                            errorBody
                        )))
                }
            } catch (e: Exception) {
                _registrationResult.value = Result.failure(e)
            } finally {

            }
        }
    }

    fun resetRegistrationResult() {
        _registrationResult.value = null
    }
}

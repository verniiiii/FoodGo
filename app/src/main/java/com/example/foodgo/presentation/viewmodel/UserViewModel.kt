package com.example.foodgo.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodgo.PreferencesManager
import com.example.foodgo.data.remote.api.UserApi
import com.example.foodgo.data.remote.dto.user.UserAddressDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val preferencesManager: PreferencesManager,
    private val userApi: UserApi
) : ViewModel() {

    private val _userName = MutableStateFlow<String?>("")
    val userName: StateFlow<String?> = _userName

    private val _userAddresses = MutableStateFlow<List<UserAddressDTO>>(emptyList())
    val userAddresses: StateFlow<List<UserAddressDTO>> = _userAddresses

    init {
        loadUserData()
    }

    fun loadUserData() {
        viewModelScope.launch {
            val token = preferencesManager.getUserToken()

            if (!token.isNullOrEmpty()) {
                try {
                    val response = userApi.getUserByToken("Bearer $token")

                    if (response.isSuccessful) {
                        val userWithAddresses = response.body()

                        if (userWithAddresses != null) {
                            val user = userWithAddresses.user
                            preferencesManager.saveUserData(
                                id = user.id!!,
                                username = user.username,
                                login = user.login,
                                description = user.profileDescription
                            )
                            _userName.value = user.username

                            _userAddresses.value = userWithAddresses.addresses
                        }
                    } else {
                        _userName.value = "Гость"
                        _userAddresses.value = emptyList()
                    }
                } catch (e: Exception) {
                    _userName.value = "Гость"
                    _userAddresses.value = emptyList()
                }
            } else {
                _userName.value = "Гость"
                _userAddresses.value = emptyList()
            }
        }
    }

    private fun loadUserAddresses() {
        viewModelScope.launch {
            val token = preferencesManager.getUserToken()

            if (!token.isNullOrEmpty()) {
                try {
                    val response = userApi.getUserByToken("Bearer $token")

                    if (response.isSuccessful) {
                        val userWithAddresses = response.body()

                        if (userWithAddresses != null) {
                            // Сохраняем адреса
                            _userAddresses.value = userWithAddresses.addresses
                        }
                    } else {
                        _userAddresses.value = emptyList()
                    }
                } catch (e: Exception) {
                    _userAddresses.value = emptyList()
                }
            } else {
                _userAddresses.value = emptyList()
            }
        }
    }

    fun deleteAddress(address: UserAddressDTO) {
        viewModelScope.launch {
            val token = preferencesManager.getUserToken() ?: return@launch
            try {
                val response = userApi.deleteUserAddress("Bearer $token", address.id ?: return@launch)
                if (response.isSuccessful) {
                    loadUserAddresses()
                }
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun addAddress(address: UserAddressDTO) {
        viewModelScope.launch {
            val token = preferencesManager.getUserToken() ?: return@launch
            try {
                val response = userApi.addUserAddress("Bearer $token", address)
                if (response.isSuccessful) {
                    loadUserAddresses()
                    _userAddresses.value = _userAddresses.value + address

                }
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}

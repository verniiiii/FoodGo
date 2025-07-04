package com.example.foodgo.data.remote.dto.user

data class LoginRequest(
    val login: String,
    val password: String
)

data class RegisterRequest(
    val login: String,
    val password: String,
    val username: String
)

data class AuthResponse(
    val token: String
)
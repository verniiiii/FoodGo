package com.example.foodgo.data.remote.dto.user

data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)
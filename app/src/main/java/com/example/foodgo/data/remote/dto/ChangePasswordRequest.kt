package com.example.foodgo.data.remote.dto

data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)

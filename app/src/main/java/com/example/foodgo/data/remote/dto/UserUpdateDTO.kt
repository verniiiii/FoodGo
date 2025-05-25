package com.example.foodgo.data.remote.dto

data class UserUpdateDTO(
    val username: String? = null,
    val password: String? = null,
    val profileDescription: String? = null
)
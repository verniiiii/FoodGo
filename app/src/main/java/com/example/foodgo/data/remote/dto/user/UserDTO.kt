package com.example.foodgo.data.remote.dto.user

data class UserDTO(
    val id: Int? = null,
    val login: String,
    val username: String,
    val profileDescription: String?
)
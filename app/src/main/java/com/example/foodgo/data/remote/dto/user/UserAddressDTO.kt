package com.example.foodgo.data.remote.dto.user

data class UserAddressDTO(
    val id: Int? = null,
    val userId: Int,
    val addressLine: String,
    val city: String,
    val comment: String? = null
)
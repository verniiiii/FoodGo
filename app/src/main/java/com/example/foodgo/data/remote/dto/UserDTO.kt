package com.example.foodgo.data.remote.dto

data class UserDTO(
    val id: Int? = null,
    val login: String,
    val username: String,
    val profileDescription: String?,
    val phoneNumber: String?
)

data class UserWithAddressesDTO(
    val user: UserDTO,
    val addresses: List<UserAddressDTO>
)


data class UserAddressDTO(
    val id: Int? = null,
    val userId: Int,
    val addressLine: String,
    val city: String,
    val comment: String? = null
)



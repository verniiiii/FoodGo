package com.example.foodgo.data.remote.dto.user

data class UserWithAddressesDTO(
    val user: UserDTO,
    val addresses: List<UserAddressDTO>
)
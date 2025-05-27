package com.example.foodgo.data.remote.dto.order

data class CartDishDTO(
    val id: Int,
    val name: String,
    val photoUrl: String,
    val size: String? = null,
    val sizePrice: Double
)
package com.example.foodgo.data.remote.dto.order

data class OrderItemDTO(
    val itemId: Long? = null,
    val dishId: Int,
    val size: String,
    val quantity: Int,
    val pricePerItem: Double
)
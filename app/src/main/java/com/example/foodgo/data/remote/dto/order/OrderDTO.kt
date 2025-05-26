package com.example.foodgo.data.remote.dto.order

data class OrderDTO(
    val orderId: Long? = null,
    val userId: Int,
    val totalPrice: Double,
    val address: String,
    val orderDate: String
)
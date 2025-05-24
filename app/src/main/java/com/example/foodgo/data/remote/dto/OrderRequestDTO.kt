package com.example.foodgo.data.remote.dto

data class OrderRequestDTO(
    val order: OrderDTO,
    val items: List<OrderItemDTO>
)

package com.example.foodgo.data.remote.dto.order

data class OrderRequestDTO(
    val order: OrderDTO,
    val items: List<OrderItemDTO>
)
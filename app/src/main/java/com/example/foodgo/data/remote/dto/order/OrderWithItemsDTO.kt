package com.example.foodgo.data.remote.dto.order

data class OrderWithItemsDTO(
    val order: OrderDTO,
    val items: List<OrderItemDTO>
)
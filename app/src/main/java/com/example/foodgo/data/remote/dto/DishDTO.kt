package com.example.foodgo.data.remote.dto

data class DishDTO(
    val id: Int,
    val name: String,
    val basePrice: Double,
    val description: String?,
    val rating: Double?,
    val category: String?,
    val photoUrl: String?
)

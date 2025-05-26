package com.example.foodgo.data.remote.dto.dish

data class FullDishDTO(
    val id: Int,
    val restaurantId: Int,
    val name: String,
    val basePrice: Double,
    val description: String,
    val rating: Double,
    val category: String?,
    val photoUrl: String,
    val sizes: List<DishSizeDTO>,
    val ingredients: List<String>
)
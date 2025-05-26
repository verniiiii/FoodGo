package com.example.foodgo.data.remote.dto.dish

data class FavoriteDishInfo(
    val dishId: Int,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val restaurantName: String
)
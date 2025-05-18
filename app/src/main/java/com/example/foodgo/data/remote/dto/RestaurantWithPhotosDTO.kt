package com.example.foodgo.data.remote.dto

data class RestaurantWithPhotosDTO(
    val id: Int,
    val name: String,
    val openingTime: String,
    val closingTime: String,
    val deliveryTimeMinutes: Int,
    val rating: Double,
    val description: String,
    val photos: List<String>,
    val categories: List<String>
)

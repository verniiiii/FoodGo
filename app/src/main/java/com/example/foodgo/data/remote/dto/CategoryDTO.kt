package com.example.foodgo.data.remote.dto

data class CategoryDTO(
    val id: Int? = null,
    val name: String,
    val photoUrl: String,
    var isSelected: Boolean = false
)

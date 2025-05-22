package com.example.foodgo.data.remote.api

import com.example.foodgo.data.remote.dto.CategoryDTO
import com.example.foodgo.data.remote.dto.DishDTO
import com.example.foodgo.data.remote.dto.RestaurantWithPhotosDTO
import com.example.foodgo.presentation.screens.home.FullDishDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DishApi {

    @GET("dishes/{id}")
    suspend fun getDishById(@Path("id") dishId: Int): Response<FullDishDTO>

}
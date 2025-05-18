package com.example.foodgo.data.remote.api

import com.example.foodgo.data.remote.dto.CategoryDTO
import com.example.foodgo.data.remote.dto.RestaurantWithPhotosDTO
import retrofit2.Response
import retrofit2.http.GET

interface RestaurantApi {
    @GET("restaurants")
    suspend fun getRestaurants(): Response<List<RestaurantWithPhotosDTO>>

    @GET("categories")
    suspend fun getCategories(): Response<List<CategoryDTO>>
}
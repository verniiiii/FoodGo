package com.example.foodgo.data.remote.api

import com.example.foodgo.data.remote.dto.CategoryDTO
import com.example.foodgo.data.remote.dto.dish.DishDTO
import com.example.foodgo.data.remote.dto.restaurant.RestaurantInfoDTO
import com.example.foodgo.data.remote.dto.restaurant.RestaurantWithPhotosDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RestaurantApi {
    @GET("restaurants")
    suspend fun getRestaurants(): Response<List<RestaurantWithPhotosDTO>>

    @GET("categories")
    suspend fun getCategories(): Response<List<CategoryDTO>>

    @GET("restaurants/{id}/dishes")
    suspend fun getDishesByRestaurant(@Path("id") restaurantId: Int): Response<List<DishDTO>>

    @GET("restaurants/{id}/info")
    suspend fun getRestaurantInfoById(@Path("id") id: Int): Response<RestaurantInfoDTO>
}
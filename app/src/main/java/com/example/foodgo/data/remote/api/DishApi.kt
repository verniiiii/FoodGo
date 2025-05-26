package com.example.foodgo.data.remote.api

import com.example.foodgo.data.remote.dto.dish.FullDishDTO
import com.example.foodgo.data.remote.dto.order.CartDishDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DishApi {

    @GET("dishes/{id}")
    suspend fun getDishById(@Path("id") dishId: Int): Response<FullDishDTO>

    @GET("dishes/{id}/size/{size}")
    suspend fun getDishWithSize(
        @Path("id") dishId: Int,
        @Path("size") size: String
    ): Response<CartDishDTO>

    @GET("dishess/{id}")
    suspend fun getDishWithoutSize(
        @Path("id") dishId: Int,
    ): Response<CartDishDTO>
}
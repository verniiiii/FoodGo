package com.example.foodgo.data.remote.api

import com.example.foodgo.data.remote.dto.order.OrderWithItemsDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApi {
    @POST("orders")
    suspend fun addOrder(
        @Body request: OrderWithItemsDTO
    ): Response<Unit>

    @GET("orders/user/{userId}")
    suspend fun getOrdersByUserId(
        @Path("userId") userId: Int
    ): Response<List<OrderWithItemsDTO>>
}


package com.example.foodgo.data.remote.api

import com.example.foodgo.data.remote.dto.order.OrderRequestDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderApi {

    @POST("orders")
    suspend fun addOrder(
        @Body request: OrderRequestDTO
    ): Response<Unit>

}

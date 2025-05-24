package com.example.foodgo.data.remote.api

import com.example.foodgo.data.remote.dto.OrderDTO
import com.example.foodgo.data.remote.dto.OrderItemDTO
import com.example.foodgo.data.remote.dto.OrderRequestDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

import retrofit2.http.*

interface OrderApi {

    // Получить все заказы
    @GET("orders")
    suspend fun getAllOrders(): Response<List<Pair<OrderDTO, List<OrderItemDTO>>>>

    // Добавить заказ с элементами (отправка двух частей: заказ и список позиций)
    @POST("orders")
    suspend fun addOrder(
        @Body request: OrderRequestDTO
    ): Response<Unit>


    // Удалить заказ по ID
    @DELETE("orders/{id}")
    suspend fun deleteOrder(@Path("id") orderId: Long): Response<Unit>
}

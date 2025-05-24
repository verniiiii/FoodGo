package com.example.foodgo.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

data class FavoriteRequestDTO(val userId: Int, val dishId: Int)

interface FavoriteApi {
    // Передаем userId как query-параметр
    @GET("favorites")
    suspend fun getFavorites(
        @Query("userId") userId: Int
    ): Response<List<Int>>


    @POST("favorites")
    suspend fun addFavorite(
        @Body request: FavoriteRequestDTO
    ): Response<Unit>

    @HTTP(method = "DELETE", path = "favorites", hasBody = true)
    suspend fun removeFavorite(
        @Body request: FavoriteRequestDTO
    ): Response<Unit>
}

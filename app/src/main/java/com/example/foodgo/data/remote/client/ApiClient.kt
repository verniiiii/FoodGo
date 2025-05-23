package com.example.foodgo.data.remote.client

import com.example.foodgo.data.remote.api.AuthApi
import com.example.foodgo.data.remote.api.RestaurantApi
import com.example.foodgo.data.remote.api.UserApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApi: AuthApi = retrofit.create(AuthApi::class.java)
    val restaurantApi: RestaurantApi = retrofit.create(RestaurantApi::class.java)
    val userApi: UserApi = retrofit.create(UserApi::class.java)
}
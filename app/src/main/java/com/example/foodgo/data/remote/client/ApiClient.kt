package com.example.foodgo.data.remote.client

import com.example.foodgo.data.remote.api.AuthApi
import com.example.foodgo.data.remote.api.DishApi
import com.example.foodgo.data.remote.api.FavoriteApi
import com.example.foodgo.data.remote.api.OrderApi
import com.example.foodgo.data.remote.api.RestaurantApi
import com.example.foodgo.data.remote.api.UserApi
import com.example.foodgo.data.remote.dto.DishDTO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.io.path.OnErrorResult

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApi: AuthApi = retrofit.create(AuthApi::class.java)
    val restaurantApi: RestaurantApi = retrofit.create(RestaurantApi::class.java)
    val userApi: UserApi = retrofit.create(UserApi::class.java)
    val dishApi: DishApi = retrofit.create(DishApi::class.java)
    val orderApi: OrderApi = retrofit.create(OrderApi::class.java)
    val favoriteApi: FavoriteApi = retrofit.create(FavoriteApi::class.java)
}
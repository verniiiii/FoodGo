package com.example.foodgo.di

import com.example.foodgo.data.remote.api.AuthApi
import com.example.foodgo.data.remote.api.RestaurantApi
import com.example.foodgo.data.remote.api.UserApi
import com.example.foodgo.data.remote.client.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideAuthApi(): AuthApi{
        return ApiClient.authApi // Испольуем клиент для предоставления экземпляра AuthApi
    }

    @Provides
    fun provideRestaurantApi(): RestaurantApi{
        return ApiClient.restaurantApi
    }

    @Provides
    fun provideUserApi(): UserApi{
        return ApiClient.userApi
    }
}
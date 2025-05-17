package com.example.foodgo.data.remote.api

import com.example.foodgo.domain.model.AuthResponse
import com.example.foodgo.domain.model.LoginRequest
import com.example.foodgo.domain.model.RegisterRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
}


package com.example.foodgo.data.remote.api

import com.example.foodgo.data.remote.dto.UserDTO
import com.example.foodgo.data.remote.dto.UserWithAddressesDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {
    suspend fun getUserNameByToken(token: String): Response<String?>

    @GET("user/info")
    suspend fun getUserByToken(@Header("Authorization") token: String): Response<UserWithAddressesDTO>
}

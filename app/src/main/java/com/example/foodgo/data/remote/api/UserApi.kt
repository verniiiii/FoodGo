package com.example.foodgo.data.remote.api

import com.example.foodgo.data.remote.dto.UserAddressDTO
import com.example.foodgo.data.remote.dto.UserDTO
import com.example.foodgo.data.remote.dto.UserWithAddressesDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    suspend fun getUserNameByToken(token: String): Response<String?>

    @GET("user/info")
    suspend fun getUserByToken(@Header("Authorization") token: String): Response<UserWithAddressesDTO>

    @DELETE("user/addresses/{id}")
    suspend fun deleteUserAddress(
        @Header("Authorization") token: String,
        @Path("id") addressId: Int
    ): Response<Unit>

    @POST("user/addresses")
    suspend fun addUserAddress(
        @Header("Authorization") token: String,
        @Body address: UserAddressDTO
    ): Response<UserAddressDTO>

}

package com.raxors.photobooth.data

import com.raxors.photobooth.data.models.request.LoginRequest
import com.raxors.photobooth.data.models.request.RegisterRequest
import com.raxors.photobooth.data.models.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface PhotoBoothApi {

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): TokenResponse

    @POST("auth/registration")
    suspend fun register(@Body registerRequest: RegisterRequest): TokenResponse

}
package com.raxors.photobooth.data.models.response


import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("expiresAt")
    val expiresAt: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("tokenType")
    val tokenType: String
)
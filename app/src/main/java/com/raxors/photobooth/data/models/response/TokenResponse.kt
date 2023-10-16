package com.raxors.photobooth.data.models.response


import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("accessToken")
    val accessToken: String? = null,
    @SerializedName("expiresAt")
    val expiresAt: String? = null,
    @SerializedName("refreshToken")
    val refreshToken: String? = null,
    @SerializedName("tokenType")
    val tokenType: String? = null
)
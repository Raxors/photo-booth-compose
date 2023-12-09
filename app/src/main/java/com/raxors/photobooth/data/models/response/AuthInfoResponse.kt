package com.raxors.photobooth.data.models.response


import com.google.gson.annotations.SerializedName

data class AuthInfoResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String
)
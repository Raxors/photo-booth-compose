package com.raxors.photobooth.data.models.request

import com.google.gson.annotations.SerializedName

data class AddUserRequest(
    @SerializedName("userId")
    val userId: String,
)

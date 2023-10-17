package com.raxors.photobooth.data.models.request

import com.google.gson.annotations.SerializedName

data class DeleteUserRequest(
    @SerializedName("userId")
    val userId: String,
)

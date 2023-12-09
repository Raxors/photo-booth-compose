package com.raxors.photobooth.data.models.request

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
    @SerializedName("newPassword")
    val newPassword: String
)

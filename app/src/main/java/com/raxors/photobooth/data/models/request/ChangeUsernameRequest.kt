package com.raxors.photobooth.data.models.request

import com.google.gson.annotations.SerializedName

data class ChangeUsernameRequest(
    @SerializedName("newUsername")
    val newUsername: String
)

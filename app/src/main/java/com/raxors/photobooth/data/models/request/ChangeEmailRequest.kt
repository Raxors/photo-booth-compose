package com.raxors.photobooth.data.models.request

import com.google.gson.annotations.SerializedName

data class ChangeEmailRequest(
    @SerializedName("newEmail")
    val newEmail: String
)

package com.raxors.photobooth.data.models.request

import com.google.gson.annotations.SerializedName

data class ChangeAvatarRequest(
    @SerializedName("file")
    val file: String
)

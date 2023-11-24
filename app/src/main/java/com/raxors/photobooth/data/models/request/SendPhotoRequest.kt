package com.raxors.photobooth.data.models.request

import com.google.gson.annotations.SerializedName

data class SendPhotoRequest(
    @SerializedName("file")
    val file: String,
    @SerializedName("userIds")
    val userIds: List<String>?,
)

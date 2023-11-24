package com.raxors.photobooth.data.models.response


import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("path")
    val path: String? = null,
    @SerializedName("thumbnailPath")
    val thumbnailPath: String? = null,
    @SerializedName("createdTime")
    val createdTime: String? = null,
    @SerializedName("ownerId")
    val ownerId: String? = null
)
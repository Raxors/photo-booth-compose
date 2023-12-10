package com.raxors.photobooth.domain.models


import com.google.gson.annotations.SerializedName

data class Image(
    val id: String,
    val path: String? = null,
    val thumbnailPath: String? = null,
    val createdTime: String? = null,
    val ownerId: String? = null
)
package com.raxors.photobooth.domain.models

data class User(
    val id: String,
    val username: String? = null,
    val name: String? = null,
    val status: String? = null,
    val imagePath: String? = null,
    val thumbnailPath: String? = null,
    val relationship: Relationship? = null
)
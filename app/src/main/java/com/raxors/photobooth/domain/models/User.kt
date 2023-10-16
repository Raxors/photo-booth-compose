package com.raxors.photobooth.domain.models

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val username: String,
    val image: String
)
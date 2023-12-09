package com.raxors.photobooth.data.models.request

import com.google.gson.annotations.SerializedName

data class ChangeProfileRequest(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("status")
    val status: String? = null,
)

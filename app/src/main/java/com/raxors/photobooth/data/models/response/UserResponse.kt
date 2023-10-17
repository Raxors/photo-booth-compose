package com.raxors.photobooth.data.models.response


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("imagePath")
    val imagePath: String? = null,
    @SerializedName("thumbnailPath")
    val thumbnailPath: String? = null,
    @SerializedName("relationship")
    val relationship: RelationshipResponse? = null
)
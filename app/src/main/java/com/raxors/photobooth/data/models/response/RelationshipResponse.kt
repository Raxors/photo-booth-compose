package com.raxors.photobooth.data.models.response

import com.google.gson.annotations.SerializedName

enum class RelationshipResponse {
    @SerializedName("STRANGER")
    STRANGER,
    @SerializedName("INCOMING_FRIEND_REQUEST")
    INCOMING_FRIEND_REQUEST,
    @SerializedName("OUTGOING_FRIEND_REQUEST")
    OUTGOING_FRIEND_REQUEST,
    @SerializedName("FRIEND")
    FRIEND,
    @SerializedName("CURRENT")
    CURRENT
}
package com.raxors.photobooth.data.models.request

import com.google.gson.annotations.SerializedName

data class SendFcmTokenRequest(
    @SerializedName("fcmToken")
    val fcmToken: String
)

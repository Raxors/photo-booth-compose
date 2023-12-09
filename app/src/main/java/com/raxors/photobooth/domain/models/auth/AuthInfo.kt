package com.raxors.photobooth.domain.models.auth

import com.raxors.photobooth.data.models.response.AuthInfoResponse

data class AuthInfo(
    val id: String,
    val username: String,
    val email: String
) {
    companion object {
        fun AuthInfoResponse.toModel() = AuthInfo(
            this.id,
            this.username,
            this.email
        )
    }
}
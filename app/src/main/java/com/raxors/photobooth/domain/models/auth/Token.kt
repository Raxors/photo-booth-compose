package com.raxors.photobooth.domain.models.auth

import com.raxors.photobooth.data.models.response.TokenResponse

data class Token(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresAt: String
) {
    companion object {
        fun TokenResponse.toModel() = Token(
            this.accessToken,
            this.refreshToken,
            this.tokenType,
            this.expiresAt
        )
    }
}
package com.raxors.photobooth.domain.models.auth

import com.raxors.photobooth.data.models.response.TokenResponse

data class Token(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val tokenType: String? = null,
    val expiresAt: String? = null
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
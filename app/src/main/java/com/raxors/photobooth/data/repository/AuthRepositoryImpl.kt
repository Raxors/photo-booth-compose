package com.raxors.photobooth.data.repository

import com.raxors.photobooth.core.utils.TokenManager
import com.raxors.photobooth.data.api.PhotoBoothApi
import com.raxors.photobooth.data.models.request.LoginRequest
import com.raxors.photobooth.data.models.request.RegisterRequest
import com.raxors.photobooth.domain.AuthRepository
import com.raxors.photobooth.domain.models.auth.Token
import com.raxors.photobooth.domain.models.auth.Token.Companion.toModel
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val api: PhotoBoothApi,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(username: String, password: String) =
        api.login(LoginRequest(username, password)).toModel()

    override suspend fun register(username: String, password: String, email: String) =
        api.register(RegisterRequest(username, password, email)).toModel()

    override suspend fun setIsLogged(isLogged: Boolean) {
        tokenManager.setIsLogged(isLogged)
    }

    override suspend fun saveToken(token: Token) {
        tokenManager.saveAccessToken(token.accessToken)
        tokenManager.saveRefreshToken(token.refreshToken)
    }

    override fun isLogged(): Flow<Boolean?> =
        tokenManager.isLogged()

    override suspend fun clearToken() {
        tokenManager.deleteAccessToken()
        tokenManager.deleteRefreshToken()
    }

}
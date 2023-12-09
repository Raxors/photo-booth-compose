package com.raxors.photobooth.data.repository

import com.raxors.photobooth.core.utils.AuthManager
import com.raxors.photobooth.data.api.PhotoBoothApi
import com.raxors.photobooth.data.models.request.ChangeEmailRequest
import com.raxors.photobooth.data.models.request.ChangePasswordRequest
import com.raxors.photobooth.data.models.request.ChangeUsernameRequest
import com.raxors.photobooth.data.models.request.LoginRequest
import com.raxors.photobooth.data.models.request.RegisterRequest
import com.raxors.photobooth.domain.AuthRepository
import com.raxors.photobooth.domain.models.auth.AuthInfo
import com.raxors.photobooth.domain.models.auth.AuthInfo.Companion.toModel
import com.raxors.photobooth.domain.models.auth.Token
import com.raxors.photobooth.domain.models.auth.Token.Companion.toModel
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val api: PhotoBoothApi,
    private val authManager: AuthManager
) : AuthRepository {

    override suspend fun login(username: String, password: String) =
        api.login(LoginRequest(username, password)).toModel()

    override suspend fun register(username: String, password: String, email: String) =
        api.register(RegisterRequest(username, password, email)).toModel()

    override suspend fun getAuthInfo() {
        val authInfo = api.getAuthInfo()
        authManager.saveAuthInfo(authInfo.id, authInfo.username, authInfo.email)
    }

    override fun getAuthInfoFromCache(): Flow<AuthInfo> =
        authManager.getAuthInfo()

    override suspend fun clearAuthInfo() {
        authManager.deleteAuthInfo()
    }

    override suspend fun setIsLogged(isLogged: Boolean) {
        authManager.setIsLogged(isLogged)
    }

    override suspend fun saveToken(token: Token) {
        authManager.saveAccessToken(token.accessToken)
        authManager.saveRefreshToken(token.refreshToken)
    }

    override fun isLogged(): Flow<Boolean> =
        authManager.isLogged()

    override suspend fun clearToken() {
        authManager.deleteAccessToken()
        authManager.deleteRefreshToken()
    }

    override suspend fun changeUsername(newUsername: String) =
        api.changeUsername(ChangeUsernameRequest(newUsername))

    override suspend fun changeEmail(newEmail: String) =
        api.changeEmail(ChangeEmailRequest(newEmail))

    override suspend fun changePassword(newPassword: String) =
        api.changePassword(ChangePasswordRequest(newPassword))

}
package com.raxors.photobooth.domain

import com.raxors.photobooth.domain.models.auth.AuthInfo
import com.raxors.photobooth.domain.models.auth.Token
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(username: String, password: String): Token
    suspend fun register(username: String, password: String, email: String): Token
    suspend fun getAuthInfo()
    fun getAuthInfoFromCache(): Flow<AuthInfo>
    suspend fun clearAuthInfo()
    suspend fun setIsLogged(isLogged: Boolean)
    suspend fun saveToken(token: Token)
    fun isLogged(): Flow<Boolean>
    suspend fun clearToken()
    suspend fun changeUsername(newUsername: String)
    suspend fun changeEmail(newEmail: String)
    suspend fun changePassword(newPassword: String)

}
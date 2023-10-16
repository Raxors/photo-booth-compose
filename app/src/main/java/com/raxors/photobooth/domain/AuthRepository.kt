package com.raxors.photobooth.domain

import com.raxors.photobooth.domain.models.auth.Token

interface AuthRepository {

    suspend fun login(username: String, password: String): Token
    suspend fun register(username: String, password: String, email: String): Token
    suspend fun setIsLogged(isLogged: Boolean)

}
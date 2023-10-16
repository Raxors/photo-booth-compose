package com.raxors.photobooth.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.raxors.photobooth.data.models.request.LoginRequest
import com.raxors.photobooth.data.models.request.RegisterRequest
import com.raxors.photobooth.domain.AuthRepository
import com.raxors.photobooth.domain.models.auth.Token.Companion.toModel

class AuthRepositoryImpl(
    private val api: PhotoBoothApi,
    private val dataStore: DataStore<Preferences>
) : AuthRepository {

    override suspend fun login(username: String, password: String) =
        api.login(LoginRequest(username, password)).toModel()

    override suspend fun register(username: String, password: String, email: String) =
        api.register(RegisterRequest(username, password, email)).toModel()

    override suspend fun setIsLogged(isLogged: Boolean) {
        dataStore.edit { settings ->
            val IS_LOGGED_KEY = booleanPreferencesKey("isLogged")
            settings[IS_LOGGED_KEY] = isLogged
        }
    }

}
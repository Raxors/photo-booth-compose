package com.raxors.photobooth.core.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.raxors.photobooth.domain.models.auth.AuthInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthManager(private val dataStore: DataStore<Preferences>) {
    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        private val ID_KEY = stringPreferencesKey("user_id")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val IS_LOGGED_KEY = booleanPreferencesKey("is_logged")
    }

    suspend fun setIsLogged(isLogged: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_KEY] = isLogged
        }
    }

    fun isLogged(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[IS_LOGGED_KEY] ?: false
        }
    }

    fun getAccessToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN_KEY]
        }
    }

    fun getRefreshToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN_KEY]
        }
    }

    suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = token
        }
    }

    suspend fun saveRefreshToken(token: String) {
        dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = token
        }
    }

    suspend fun deleteAccessToken() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
        }
    }

    suspend fun deleteRefreshToken() {
        dataStore.edit { preferences ->
            preferences.remove(REFRESH_TOKEN_KEY)
        }
    }

    suspend fun saveAuthInfo(userId: String, username: String, email: String) {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = userId
            preferences[USERNAME_KEY] = username
            preferences[EMAIL_KEY] = email
        }
    }

    suspend fun deleteAuthInfo() {
        dataStore.edit { preferences ->
            preferences.remove(ID_KEY)
            preferences.remove(USERNAME_KEY)
            preferences.remove(EMAIL_KEY)
        }
    }

    fun getAuthInfo(): Flow<AuthInfo> {
        return dataStore.data.map { preferences ->
            val id = preferences[ID_KEY] ?: ""
            val username = preferences[USERNAME_KEY] ?: ""
            val email = preferences[EMAIL_KEY] ?: ""
            AuthInfo(id, username, email)
        }
    }

}
package com.raxors.photobooth.core.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenManager(private val dataStore: DataStore<Preferences>) {
    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
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
}
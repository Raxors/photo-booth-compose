package com.raxors.photobooth.core.utils.network

import com.raxors.photobooth.core.utils.AuthManager
import com.raxors.photobooth.data.api.PhotoBoothApi
import com.raxors.photobooth.data.models.response.TokenResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val authManager: AuthManager,
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking {
            authManager.getRefreshToken().first()
        }
        return runBlocking {
            val newToken = getNewToken(token)
            if (!newToken.isSuccessful || newToken.body() == null) {
                authManager.deleteAccessToken()
                authManager.deleteRefreshToken()
            }
            newToken.body()?.let {
                authManager.saveAccessToken(it.accessToken)
                authManager.saveRefreshToken(it.refreshToken)
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.accessToken}")
                    .build()
            }
        }
    }
    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<TokenResponse> {
        val okHttpClient = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://167.172.106.38:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(PhotoBoothApi::class.java)
        return service.refreshToken("Bearer $refreshToken")
    }
}
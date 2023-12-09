package com.raxors.photobooth.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.raxors.photobooth.BuildConfig
import com.raxors.photobooth.core.utils.AuthManager
import com.raxors.photobooth.core.utils.network.AuthAuthenticator
import com.raxors.photobooth.core.utils.network.AuthInterceptor
import com.raxors.photobooth.data.api.PhotoBoothApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .addInterceptor(ChuckerInterceptor(context))
            .authenticator(authAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_HOST + BuildConfig.API_VERSION)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): PhotoBoothApi {
        return retrofit.create(PhotoBoothApi::class.java)
    }

    @Singleton
    @Provides
    fun provideTokenManager(dataStore: DataStore<Preferences>): AuthManager =
        AuthManager(dataStore)

    @Singleton
    @Provides
    fun provideAuthInterceptor(authManager: AuthManager): AuthInterceptor =
        AuthInterceptor(authManager)

    @Singleton
    @Provides
    fun provideAuthAuthenticator(authManager: AuthManager): AuthAuthenticator =
        AuthAuthenticator(authManager)
}

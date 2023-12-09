package com.raxors.photobooth.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.raxors.photobooth.core.utils.AuthManager
import com.raxors.photobooth.data.repository.AppRepositoryImpl
import com.raxors.photobooth.data.repository.AuthRepositoryImpl
import com.raxors.photobooth.data.api.PhotoBoothApi
import com.raxors.photobooth.domain.AppRepository
import com.raxors.photobooth.domain.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideAuthRepository(
        api: PhotoBoothApi,
        authManager: AuthManager
    ): AuthRepository = AuthRepositoryImpl(api, authManager)

    @Provides
    fun provideAppRepository(
        api: PhotoBoothApi,
        dataStore: DataStore<Preferences>,
    ): AppRepository = AppRepositoryImpl(api, dataStore)

}
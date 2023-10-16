package com.raxors.photobooth.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.raxors.photobooth.data.AuthRepositoryImpl
import com.raxors.photobooth.data.PhotoBoothApi
import com.raxors.photobooth.domain.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideAuthRepository(
        api: PhotoBoothApi,
        dataStore: DataStore<Preferences>
    ): AuthRepository = AuthRepositoryImpl(api, dataStore)

}
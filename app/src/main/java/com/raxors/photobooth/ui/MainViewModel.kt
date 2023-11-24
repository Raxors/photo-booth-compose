package com.raxors.photobooth.ui

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raxors.photobooth.domain.AuthRepository
import com.raxors.photobooth.domain.models.auth.Token
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            authRepo.clearToken()
            authRepo.setIsLogged(false)
        }
    }

    val isLogged: Flow<Boolean?> = authRepo.isLogged()

}
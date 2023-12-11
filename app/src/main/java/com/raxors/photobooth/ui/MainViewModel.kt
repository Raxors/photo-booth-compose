package com.raxors.photobooth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.raxors.photobooth.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {
    fun logout() {
        FirebaseMessaging.getInstance().deleteToken()
        viewModelScope.launch {
            authRepo.clearAuthInfo()
            authRepo.clearToken()
            authRepo.setIsLogged(false)
        }
    }

    val isLogged: Flow<Boolean> = authRepo.isLogged()

}
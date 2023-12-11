package com.raxors.photobooth.ui.screens.splash

import androidx.lifecycle.ViewModel
import com.raxors.photobooth.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {

    val isLogged: Flow<Boolean> = authRepo.isLogged()

}
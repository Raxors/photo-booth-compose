package com.raxors.photobooth.ui.screens.auth.login

import com.raxors.photobooth.core.ScreenEvent
import com.raxors.photobooth.ui.screens.auth.registration.RegistrationScreenEvent

sealed interface LoginScreenEvent : ScreenEvent {
    data class OnUsernameChanged(val value: String) : LoginScreenEvent
    data class OnPasswordChanged(val value: String) : LoginScreenEvent
    data class OnLoginClicked(val username: String, val password: String) : LoginScreenEvent
    data object OnRegisterClicked : LoginScreenEvent
}
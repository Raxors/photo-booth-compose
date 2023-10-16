package com.raxors.photobooth.ui.screens.auth.registration

import com.raxors.photobooth.core.ScreenEvent

sealed interface RegistrationScreenEvent : ScreenEvent {
    data class OnUsernameChanged(val value: String) : RegistrationScreenEvent
    data class OnPasswordChanged(val value: String) : RegistrationScreenEvent
    data class OnEmailChanged(val value: String) : RegistrationScreenEvent
    data class OnRegistrationClicked(
        val username: String,
        val password: String,
        val email: String
    ) : RegistrationScreenEvent
}
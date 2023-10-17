package com.raxors.photobooth.ui.screens.auth.registration

import com.raxors.photobooth.core.UiEvent

sealed interface RegistrationUiEvent : UiEvent {
    data class OnUsernameChanged(val value: String) : RegistrationUiEvent
    data class OnPasswordChanged(val value: String) : RegistrationUiEvent
    data class OnEmailChanged(val value: String) : RegistrationUiEvent
    data class OnRegistrationClicked(
        val username: String,
        val password: String,
        val email: String
    ) : RegistrationUiEvent
}
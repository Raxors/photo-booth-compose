package com.raxors.photobooth.ui.screens.auth.login

import com.raxors.photobooth.core.UiEvent

sealed interface LoginUiEvent : UiEvent {
    data class OnUsernameChanged(val value: String) : LoginUiEvent
    data class OnPasswordChanged(val value: String) : LoginUiEvent
    data class OnLoginClicked(val username: String, val password: String) : LoginUiEvent
    data object OnRegisterClicked : LoginUiEvent
}
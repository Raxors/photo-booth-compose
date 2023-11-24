package com.raxors.photobooth.ui.screens.profile

import com.raxors.photobooth.core.UiEvent

sealed interface ProfileUiEvent : UiEvent {
    data object OnLogout : ProfileUiEvent
}
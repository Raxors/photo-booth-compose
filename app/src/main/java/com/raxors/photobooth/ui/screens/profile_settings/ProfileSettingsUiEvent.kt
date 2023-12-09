package com.raxors.photobooth.ui.screens.profile_settings

import com.raxors.photobooth.core.UiEvent

sealed interface ProfileSettingsUiEvent : UiEvent {
    data class IsEditUsernameShow(val isShow: Boolean) : ProfileSettingsUiEvent
    data class IsEditEmailShow(val isShow: Boolean) : ProfileSettingsUiEvent
    data class IsEditPasswordShow(val isShow: Boolean) : ProfileSettingsUiEvent
}
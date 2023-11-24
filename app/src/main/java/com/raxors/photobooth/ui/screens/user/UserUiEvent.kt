package com.raxors.photobooth.ui.screens.user

import com.raxors.photobooth.core.UiEvent

sealed interface UserUiEvent : UiEvent {
    data object OnAddUser : UserUiEvent
    data object OnDeleteUser : UserUiEvent
}
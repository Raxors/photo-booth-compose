package com.raxors.photobooth.ui.screens.history

import com.raxors.photobooth.core.UiEvent

sealed interface HistoryUiEvent : UiEvent {
    data object OnImageClicked : HistoryUiEvent
}
package com.raxors.photobooth.ui.screens.history

import com.raxors.photobooth.core.UiState
import com.raxors.photobooth.domain.models.User

data class HistoryUiState(
    val isLoading: Boolean = false
): UiState
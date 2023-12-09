package com.raxors.photobooth.ui.screens.history_detail

import com.raxors.photobooth.core.UiState
import com.raxors.photobooth.domain.models.Image
import com.raxors.photobooth.domain.models.User

data class HistoryDetailUiState(
    val isLoading: Boolean = false,
    val image: Image? = null,
    val user: User? = null
): UiState
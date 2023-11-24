package com.raxors.photobooth.ui.screens.history

import com.raxors.photobooth.core.UiState
import com.raxors.photobooth.domain.models.Image
import com.raxors.photobooth.domain.models.User

data class HistoryUiState(
    val imageList: List<Image> = listOf(),
    val isLoading: Boolean = true
): UiState
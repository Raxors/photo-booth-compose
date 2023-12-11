package com.raxors.photobooth.ui.screens.history

import androidx.paging.PagingData
import com.raxors.photobooth.core.UiState
import com.raxors.photobooth.domain.models.Image

data class HistoryUiState(
    val imageList: PagingData<Image> = PagingData.empty(),
    val isLoading: Boolean = true
): UiState
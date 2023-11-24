package com.raxors.photobooth.ui.screens.camera.bottomsheet

import androidx.paging.PagingData
import com.raxors.photobooth.core.UiState
import com.raxors.photobooth.domain.models.User
import kotlinx.coroutines.flow.MutableStateFlow

data class SendImageUiState(
    val selectedFriends: Set<User> = setOf(),
    val isAllChecked: Boolean = true,
    val isPhotoSent: Boolean = false
): UiState
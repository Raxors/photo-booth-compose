package com.raxors.photobooth.ui.screens.profile

import com.raxors.photobooth.core.UiState
import com.raxors.photobooth.domain.models.User

data class ProfileUiState(
    val profile: User? = null,
    val isLoading: Boolean = false
): UiState
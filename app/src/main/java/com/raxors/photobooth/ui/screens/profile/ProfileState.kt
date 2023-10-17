package com.raxors.photobooth.ui.screens.profile

import com.raxors.photobooth.core.UiState
import com.raxors.photobooth.domain.models.User

data class ProfileState(
    val profile: User? = null,
    val isLoading: Boolean = false
): UiState
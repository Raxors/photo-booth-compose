package com.raxors.photobooth.ui.screens.user

import com.raxors.photobooth.core.UiState
import com.raxors.photobooth.domain.models.User

data class UserUiState(
    val isLoading: Boolean = false,
    val user: User? = null
): UiState
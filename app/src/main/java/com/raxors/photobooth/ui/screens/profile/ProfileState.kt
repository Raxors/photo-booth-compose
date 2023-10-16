package com.raxors.photobooth.ui.screens.profile

import com.raxors.photobooth.core.ScreenState
import com.raxors.photobooth.domain.models.User

data class ProfileState(
    val profile: User? = null,
    val isLoading: Boolean = false
): ScreenState
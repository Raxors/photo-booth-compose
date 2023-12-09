package com.raxors.photobooth.ui.screens.profile_settings

import com.raxors.photobooth.core.UiState
import com.raxors.photobooth.domain.models.User

data class ProfileSettingsUiState(
    val username: String = "",
    val email: String = "",
    val isShowEditUsername: Boolean = false,
    val isShowEditEmail: Boolean = false,
    val isShowEditPassword: Boolean = false
): UiState
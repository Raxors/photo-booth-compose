package com.raxors.photobooth.ui.screens.auth.login

import com.raxors.photobooth.core.UiState

data class LoginUiState(
    val username: String? = null,
    val password: String? = null,
    val usernameError: String? = null,
    val passwordError: String? = null,
    val navigationRoute: String? = null,
): UiState
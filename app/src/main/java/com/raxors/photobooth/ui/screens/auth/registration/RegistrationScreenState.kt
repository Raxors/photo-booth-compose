package com.raxors.photobooth.ui.screens.auth.registration

import com.raxors.photobooth.core.ScreenState

data class RegistrationScreenState(
    val username: String? = null,
    val password: String? = null,
    val email: String? = null,
    val usernameError: String? = null,
    val passwordError: String? = null,
    val emailError: String? = null,
    val navigationRoute: String? = null
): ScreenState
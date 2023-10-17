package com.raxors.photobooth.ui.screens.auth.registration

import com.raxors.photobooth.core.base.BaseViewModel
import com.raxors.photobooth.core.utils.Security.sha256
import com.raxors.photobooth.domain.AuthRepository
import com.raxors.photobooth.ui.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repo: AuthRepository
) : BaseViewModel<RegistrationUiState, RegistrationUiEvent>() {

    override fun initialState(): RegistrationUiState = RegistrationUiState()

    fun onEvent(event: RegistrationUiEvent) {
        when (event) {
            is RegistrationUiEvent.OnUsernameChanged -> {
                setState { copy(username = event.value) }
            }

            is RegistrationUiEvent.OnPasswordChanged -> {
                setState { copy(password = event.value) }
            }

            is RegistrationUiEvent.OnEmailChanged -> {
                setState { copy(email = event.value) }
            }

            is RegistrationUiEvent.OnRegistrationClicked -> {
                register(event.username, event.password, event.email)
            }
        }
    }

    private fun register(username: String, password: String, email: String) {
        launch({
            val token = repo.register(username, password.sha256(), email)
            setState { copy(navigationRoute = Screen.MainScreen.route) }
        }, onError = {

        })
    }

    fun resetNavigationRoute() {
        setState { copy(navigationRoute = null) }
    }
}
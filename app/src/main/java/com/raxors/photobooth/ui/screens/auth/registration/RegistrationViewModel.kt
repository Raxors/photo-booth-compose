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
) : BaseViewModel<RegistrationScreenState, RegistrationScreenEvent>() {

    override fun initialState(): RegistrationScreenState = RegistrationScreenState()

    fun onEvent(event: RegistrationScreenEvent) {
        when (event) {
            is RegistrationScreenEvent.OnUsernameChanged -> {
                setState { copy(username = event.value) }
            }

            is RegistrationScreenEvent.OnPasswordChanged -> {
                setState { copy(password = event.value) }
            }

            is RegistrationScreenEvent.OnEmailChanged -> {
                setState { copy(email = event.value) }
            }

            is RegistrationScreenEvent.OnRegistrationClicked -> {
                register(event.username, event.password, event.email)
            }
        }
    }

    private fun register(username: String, password: String, email: String) {
        launch({
            val token = repo.register(username, password.sha256(), email)
            setState { copy(navigationRoute = Screen.MainScreen.route) }
        }, error = {

        })
    }

    fun resetNavigationRoute() {
        setState { copy(navigationRoute = null) }
    }
}
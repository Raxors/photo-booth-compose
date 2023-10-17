package com.raxors.photobooth.ui.screens.auth.login

import com.raxors.photobooth.core.base.BaseViewModel
import com.raxors.photobooth.core.utils.Security.sha256
import com.raxors.photobooth.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: AuthRepository
) : BaseViewModel<LoginUiState, LoginUiEvent>() {

    override fun initialState(): LoginUiState = LoginUiState()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.OnUsernameChanged -> {
                setState { copy(username = event.value) }
            }

            is LoginUiEvent.OnPasswordChanged -> {
                setState { copy(password = event.value) }
            }

            is LoginUiEvent.OnLoginClicked -> {
                login(event.username, event.password)
            }

            LoginUiEvent.OnRegisterClicked -> {
                setState { copy(navigationRoute = "registration") }
            }
        }
    }

    private fun login(username: String, password: String) {
        launch({
            val token = repo.login(username, password.sha256())
            repo.saveToken(token)
            repo.setIsLogged(true)
        }, onError = {

        })
    }

    fun resetNavigationRoute() {
        setState { copy(navigationRoute = null) }
    }
}
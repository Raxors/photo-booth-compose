package com.raxors.photobooth.ui.screens.auth.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raxors.photobooth.core.base.BaseViewModel
import com.raxors.photobooth.core.utils.Security.sha256
import com.raxors.photobooth.domain.AuthRepository
import com.raxors.photobooth.ui.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: AuthRepository
) : BaseViewModel<LoginScreenState, LoginScreenEvent>() {

    override fun initialState(): LoginScreenState = LoginScreenState()

    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.OnUsernameChanged -> {
                setState { copy(username = event.value) }
            }

            is LoginScreenEvent.OnPasswordChanged -> {
                setState { copy(password = event.value) }
            }

            is LoginScreenEvent.OnLoginClicked -> {
                login(event.username, event.password)
            }

            LoginScreenEvent.OnRegisterClicked -> {
                setState { copy(navigationRoute = "registration") }
            }
        }
    }

    private fun login(username: String, password: String) {
        launch({
            repo.setIsLogged(true)
            val token = repo.login(username, password.sha256())
        }, error = {

        })
    }

    fun resetNavigationRoute() {
        setState { copy(navigationRoute = null) }
    }
}
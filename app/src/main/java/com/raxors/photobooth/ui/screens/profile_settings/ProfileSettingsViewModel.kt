package com.raxors.photobooth.ui.screens.profile_settings

import androidx.lifecycle.SavedStateHandle
import com.raxors.photobooth.core.base.BaseViewModel
import com.raxors.photobooth.core.utils.Security.sha256
import com.raxors.photobooth.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class ProfileSettingsViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<ProfileSettingsUiState, ProfileSettingsUiEvent>() {

    init {
        launch({
            repo.getAuthInfoFromCache().collectLatest {
                setState { copy(username = it.username, email = it.email) }
            }
        })
    }

    override fun initialState(): ProfileSettingsUiState = ProfileSettingsUiState()

    fun onEvent(event: ProfileSettingsUiEvent) {
        when (event) {
            is ProfileSettingsUiEvent.IsEditUsernameShow -> setState { copy(isShowEditUsername = event.isShow) }
            is ProfileSettingsUiEvent.IsEditEmailShow -> setState { copy(isShowEditEmail = event.isShow) }
            is ProfileSettingsUiEvent.IsEditPasswordShow -> setState { copy(isShowEditPassword = event.isShow) }
        }
    }

    fun changeUsername(username: String) {
        launch({
            repo.changeUsername(username)
        }, onError = {
            //TODO handle errors
        })
    }

    fun changeEmail(email: String) {
        launch({
            repo.changeEmail(email)
        }, onError = {
            //TODO handle errors
        })
    }

    fun changePassword(password: String) {
        launch({
            repo.changePassword(password.sha256())
        }, onError = {
            //TODO handle errors
        })
    }

}
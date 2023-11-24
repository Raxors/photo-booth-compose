package com.raxors.photobooth.ui.screens.profile

import androidx.lifecycle.SavedStateHandle
import com.raxors.photobooth.core.base.BaseViewModel
import com.raxors.photobooth.domain.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: AppRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<ProfileUiState, ProfileUiEvent>() {

    override fun initialState(): ProfileUiState = ProfileUiState(isLoading = true)

    init {
        getProfile()
    }

    fun onEvent(event: ProfileUiEvent) {
        when (event) {
            is ProfileUiEvent.OnLogout -> {
            }
        }
    }

    private fun getProfile() {
        launch({
            val profile = repo.getProfile()
            setState { copy(isLoading = false, profile = profile) }
        }, onError = {
            //TODO handle errors
        })
    }

}
package com.raxors.photobooth.ui.screens.profile

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import com.raxors.photobooth.core.base.BaseViewModel
import com.raxors.photobooth.core.utils.Extensions.encodeImage
import com.raxors.photobooth.domain.AppRepository
import com.raxors.photobooth.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: AppRepository,
    private val authRepo: AuthRepository,
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
            is ProfileUiEvent.IsEditNameShow -> {
                setState { copy(isShowEditName = event.isShow) }
            }
        }
    }

    private fun getProfile() {
        launch({
            authRepo.getAuthInfo()
            val profile = repo.getProfile()
            setState { copy(isLoading = false, profile = profile) }
        }, onError = {
            //TODO handle errors
        })
    }

    fun changeName(name: String) {
        launch({
            repo.changeProfile(name)
            getProfile()
            setState { copy(isShowEditName = false) }
        }, onError = {
            //TODO handle errors
        })
    }

    fun changeAvatar(bitmap: Bitmap) {
        launch({
            repo.changeAvatar(bitmap.encodeImage())
            getProfile()
        }, onError = {
            //TODO handle errors
        })
    }

}
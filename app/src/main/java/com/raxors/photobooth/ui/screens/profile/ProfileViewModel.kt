package com.raxors.photobooth.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.raxors.photobooth.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(ProfileState(isLoading = true))
    val state: StateFlow<ProfileState>
        get() = _state.asStateFlow()

    init {
        getProfile()
    }

    private fun getProfile() {
        val user = User(
            "myId", "Name", "Surname", "Pinus",
            "https://cdn.leroymerlin.ru/lmru/image/upload/v1694419671/b_white,c_pad,d_photoiscoming.png,f_auto,h_600,q_auto,w_600/lmcode/y6oYV1zCTUeJxo223hwX0w/91313845.jpg"
        )
        _state.update { it.copy(profile = user, isLoading = false) }
    }

}
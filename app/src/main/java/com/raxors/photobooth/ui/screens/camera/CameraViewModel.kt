package com.raxors.photobooth.ui.screens.camera

import com.raxors.photobooth.core.base.BaseViewModel
import com.raxors.photobooth.domain.AppRepository
import com.raxors.photobooth.ui.screens.user.UserUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val repo: AppRepository,
): BaseViewModel<CameraUiState, CameraUiEvent>() {
    override fun initialState(): CameraUiState = CameraUiState()

    fun onEvent(event: CameraUiEvent) {
        when (event) {
            is CameraUiEvent.OnTakePhoto -> {
                setState { copy(bitmap = event.bitmap, showSheet = true) }
            }
            CameraUiEvent.OnCloseBottomSheet -> {
                setState { copy(bitmap = null, showSheet = false) }
            }
            is CameraUiEvent.ChangeCamera -> {
                setState { copy(isFrontCamera = event.isFrontCamera) }
            }
        }
    }

    fun sendImageToFriends(base64Image: String) {
        launch({
            repo.sendPhoto(null, base64Image)
        }, onError = {

        })
    }
}
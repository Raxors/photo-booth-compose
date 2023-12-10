package com.raxors.photobooth.ui.screens.camera

import com.raxors.photobooth.core.base.BaseViewModel
import com.raxors.photobooth.domain.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val repo: AppRepository,
): BaseViewModel<CameraUiState, CameraUiEvent>() {
    override fun initialState(): CameraUiState = CameraUiState()

    val fcmToken = repo.getFcmToken()

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
}
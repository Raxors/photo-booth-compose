package com.raxors.photobooth.ui.screens.camera

import androidx.compose.ui.graphics.ImageBitmap
import com.raxors.photobooth.core.UiEvent

sealed interface CameraUiEvent : UiEvent {
    data class OnTakePhoto(val bitmap: ImageBitmap) : CameraUiEvent
    data object OnCloseBottomSheet : CameraUiEvent
    data class ChangeCamera(val isFrontCamera: Boolean): CameraUiEvent
}
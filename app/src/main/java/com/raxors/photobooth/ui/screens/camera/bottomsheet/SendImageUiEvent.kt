package com.raxors.photobooth.ui.screens.camera.bottomsheet

import android.graphics.Bitmap
import com.raxors.photobooth.core.UiEvent
import com.raxors.photobooth.domain.models.User

sealed interface SendImageUiEvent : UiEvent {
    data object CheckAll : SendImageUiEvent
    data class CheckUser(val user: User) : SendImageUiEvent
    data object CloseBottomSheet : SendImageUiEvent
    data class SendPhoto(val bitmap: Bitmap) : SendImageUiEvent
}
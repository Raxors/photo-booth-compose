package com.raxors.photobooth.ui.screens.camera

import androidx.compose.ui.graphics.ImageBitmap
import com.raxors.photobooth.core.UiState

data class CameraUiState(
    val isFrontCamera: Boolean = true,
    val bitmap: ImageBitmap? = null,
    val showSheet: Boolean = false
): UiState
package com.raxors.photobooth.ui.screens.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Base64
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.raxors.photobooth.core.navigation.CommonScreen
import com.raxors.photobooth.ui.screens.camera.bottomsheet.SendImageBottomSheet
import com.raxors.photobooth.ui.screens.camera.components.CameraPreview
import java.io.ByteArrayOutputStream

@Composable
fun CameraScreen(
    navHostController: NavHostController,
    context: Context,
    viewModel: CameraViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    if (state.showSheet) {
        SendImageBottomSheet(
            bitmap = state.bitmap,
            onDismiss = {
                viewModel.onEvent(CameraUiEvent.OnCloseBottomSheet)
            })
    }
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE
            )
            cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                   navHostController.navigate(CommonScreen.Profile.route)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            Image(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Profile",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CameraPreview(
                controller = controller,
                modifier = Modifier
                    .size(300.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(Modifier.size(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(Modifier.size(64.dp))
                Spacer(Modifier.size(16.dp))
                IconButton(
                    onClick = {
                        controller.takePicture(
                            ContextCompat.getMainExecutor(context),
                            object : ImageCapture.OnImageCapturedCallback() {
                                override fun onCaptureSuccess(image: ImageProxy) {
                                    val bitmap = image.toBitmap()
                                    viewModel.onEvent(
                                        CameraUiEvent.OnTakePhoto(
                                            cropToSquare(
                                                bitmap,
                                                state.isFrontCamera
                                            ).asImageBitmap()
                                        )
                                    )
                                    super.onCaptureSuccess(image)
                                }
                            })
                    },
                    modifier = Modifier
                        .size(64.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.RadioButtonChecked,
                        contentDescription = "Take photo",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(Modifier.size(16.dp))
                IconButton(
                    onClick = {
                        controller.cameraSelector =
                            if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                                viewModel.onEvent(CameraUiEvent.ChangeCamera(true))
                                CameraSelector.DEFAULT_FRONT_CAMERA
                            } else {
                                viewModel.onEvent(CameraUiEvent.ChangeCamera(false))
                                CameraSelector.DEFAULT_BACK_CAMERA
                            }
                    },
                    modifier = Modifier
                        .size(64.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Cameraswitch,
                        contentDescription = "Switch camera"
                    )
                }
            }
        }
    }
}

fun cropToSquare(bitmap: Bitmap, isFrontCamera: Boolean): Bitmap {
    val width = bitmap.width
    val height = bitmap.height
    val newWidth = if (height > width) width else height
    val newHeight = if (height > width) height - (height - width) else height
    var cropW = (width - height) / 2
    cropW = if (cropW < 0) 0 else cropW
    var cropH = (height - width) / 2
    cropH = if (cropH < 0) 0 else cropH
    val matrix = Matrix()
    matrix.postRotate(if (isFrontCamera) -90f else 90f)
    matrix.postScale(
        (if (isFrontCamera) -1 else 1).toFloat(),
        1f,
        bitmap.width / 2f,
        bitmap.height / 2f
    )
    return Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight, matrix, true)
}
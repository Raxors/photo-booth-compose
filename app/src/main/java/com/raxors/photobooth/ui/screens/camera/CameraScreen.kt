package com.raxors.photobooth.ui.screens.camera

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.raxors.photobooth.ui.screens.camera.components.CameraPreview

@Composable
fun CameraScreen(
    navHostController: NavHostController,
    context: Context
) {
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or
                        CameraController.VIDEO_CAPTURE
            )
            cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        }
    }
    val viewModel = viewModel<CameraViewModel>()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CameraPreview(
            controller = controller,
            modifier = Modifier
                .size(256.dp)
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
//                    controller.unbind()
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
                            CameraSelector.DEFAULT_FRONT_CAMERA
                        } else CameraSelector.DEFAULT_BACK_CAMERA
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
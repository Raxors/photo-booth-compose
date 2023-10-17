package com.raxors.photobooth.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.raxors.photobooth.ui.screens.camera.CameraViewModel

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    viewModel: ProfileViewModel = viewModel(),
    logout: () -> Unit
) {
//    val viewModel = viewModel<ProfileViewModel>()
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        state.profile?.let {
            with(it) {
                AsyncImage(
                    model = imagePath,
                    contentDescription = "User avatar",
                    modifier = Modifier.size(256.dp).clip(CircleShape)
                )
                Text(text = "$name")
                Text(text = "@$username")
            }
        }
    }
}
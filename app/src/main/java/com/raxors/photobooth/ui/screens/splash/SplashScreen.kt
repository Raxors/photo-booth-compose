package com.raxors.photobooth.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.raxors.photobooth.R
import com.raxors.photobooth.core.utils.Extensions.observeAsEvent
import com.raxors.photobooth.ui.Screen

@Composable
fun SplashScreen(
    navHostController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    viewModel.isLogged.observeAsEvent {
        navHostController.navigate(if (it) Screen.MainScreen.route else Screen.LoginScreen.route) {
            popUpTo(0)
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "App logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize(0.5f)
        )
    }
}
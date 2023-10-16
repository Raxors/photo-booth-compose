package com.raxors.photobooth.core.navigation

import android.content.Context
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.raxors.photobooth.ui.screens.camera.CameraScreen
import com.raxors.photobooth.ui.screens.friendlist.FriendListScreen
import com.raxors.photobooth.ui.screens.profile.ProfileScreen

@Composable
fun NavigationHost(
    context: Context,
    navHostController: NavHostController,
    modifier: Modifier,
    logout: () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = NavigationScreen.Camera.route,
        modifier = modifier
    ) {
        composable(route = NavigationScreen.FriendList.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }) { FriendListScreen(navHostController) }
        composable(route = NavigationScreen.Camera.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }) { CameraScreen(navHostController, context) }
        composable(route = NavigationScreen.Profile.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }) { ProfileScreen(navHostController) { logout() } }
    }
}
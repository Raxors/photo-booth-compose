package com.raxors.photobooth.core.navigation

import android.content.Context
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.raxors.photobooth.ui.screens.camera.CameraScreen
import com.raxors.photobooth.ui.screens.friendlist.FriendListScreen
import com.raxors.photobooth.ui.screens.history.HistoryScreen
import com.raxors.photobooth.ui.screens.profile.ProfileScreen
import com.raxors.photobooth.ui.screens.user.UserScreen

@Composable
fun NavigationHost(
    context: Context,
    navHostController: NavHostController,
    modifier: Modifier,
    bottomBarState: MutableState<Boolean>,
    logout: () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = BottomNavScreen.Camera.route,
        modifier = modifier
    ) {
        composable(
            route = BottomNavScreen.FriendList.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
            content = {
                bottomBarState.value = true
                FriendListScreen(navHostController)
            }
        )
        composable(
            route = BottomNavScreen.Camera.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
            content = {
                bottomBarState.value = true
                CameraScreen(
                    navHostController,
                    context
                )
            }
        )
        composable(
            route = BottomNavScreen.History.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
            content = {
                bottomBarState.value = true
                HistoryScreen(navHostController)
            }
        )
        composable(
            route = CommonScreen.User.commonRoute,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
            content = {
                bottomBarState.value = false
                UserScreen(navHostController)
            }
        )
        composable(
            route = CommonScreen.Profile.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
            content = {
                bottomBarState.value = false
                ProfileScreen(navHostController, logout)
            }
        )
    }
}
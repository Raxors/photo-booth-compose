package com.raxors.photobooth.core.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.ui.graphics.vector.ImageVector
import com.raxors.photobooth.R

sealed class NavigationScreen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    data object FriendList : NavigationScreen("friendList", R.string.friend_list, Icons.Filled.People)
    data object Camera : NavigationScreen("camera", R.string.camera, Icons.Filled.PhotoCamera)
    data object Profile : NavigationScreen("profile", R.string.profile, Icons.Filled.Person)
}
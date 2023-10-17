package com.raxors.photobooth.core.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.outlined.Dataset
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.ui.graphics.vector.ImageVector
import com.raxors.photobooth.R

sealed class NavigationScreen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    data object FriendList : NavigationScreen("friendList", R.string.friend_list, Icons.Outlined.People)
    data object Camera : NavigationScreen("camera", R.string.camera, Icons.Outlined.PhotoCamera)
    data object History : NavigationScreen("history", R.string.history, Icons.Outlined.Dataset)
}
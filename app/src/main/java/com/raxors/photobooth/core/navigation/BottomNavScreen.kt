package com.raxors.photobooth.core.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.outlined.Dataset
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.ui.graphics.vector.ImageVector
import com.raxors.photobooth.R

sealed class BottomNavScreen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    data object FriendList :
        BottomNavScreen("friendList", R.string.friend_list, Icons.Filled.People)

    data object Camera : BottomNavScreen("camera", R.string.camera, Icons.Filled.PhotoCamera)
    data object History : BottomNavScreen("history", R.string.history, Icons.Outlined.Dataset)
}
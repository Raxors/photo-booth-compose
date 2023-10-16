package com.raxors.photobooth.ui.screens.friendlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun FriendListScreen(
    navHostController: NavHostController
) {
    Box(Modifier.fillMaxSize()) {
        Text("Friend List Screen")
    }
}
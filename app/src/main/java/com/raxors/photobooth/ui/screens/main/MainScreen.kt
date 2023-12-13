package com.raxors.photobooth.ui.screens.main

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.raxors.photobooth.core.navigation.NavigationHost

@Composable
fun MainScreen(
    context: Context,
    imageId: String? = null,
    logout: () -> Unit
) {
    val navBarNavController = rememberNavController()
    val bottomBarState = rememberSaveable { mutableStateOf(true) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(
                navHostController = navBarNavController,
                bottomBarState = bottomBarState
            )
        },
    ) { innerPadding ->
        NavigationHost(
            context = context,
            navHostController = navBarNavController,
            modifier = Modifier.padding(innerPadding),
            bottomBarState = bottomBarState,
            imageId = imageId
        ) {
            logout()
        }
    }

}
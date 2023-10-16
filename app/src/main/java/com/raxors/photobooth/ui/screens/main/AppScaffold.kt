package com.raxors.photobooth.ui.screens.main

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.raxors.photobooth.core.navigation.NavigationHost

@Composable
fun AppScaffold(
    context: Context,
    navHostController: NavHostController,
    logout: () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomBar(navHostController = navHostController)
        },
    ) { innerPadding ->
        NavigationHost(
            context = context,
            navHostController = navHostController,
            modifier = Modifier.padding(innerPadding)
        ) {
            logout()
        }
    }

}
package com.raxors.photobooth.ui.screens.main

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberBottomAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.raxors.photobooth.core.navigation.NavigationHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    context: Context,
    logout: () -> Unit
) {
    val navBarNavController = rememberNavController()
    val bottomBarState = rememberSaveable { mutableStateOf(true) }
    Scaffold(
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
            bottomBarState = bottomBarState
        ) {
            logout()
        }
    }

}
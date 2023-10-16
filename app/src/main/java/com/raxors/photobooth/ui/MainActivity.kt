package com.raxors.photobooth.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raxors.photobooth.ui.screens.auth.login.LoginScreen
import com.raxors.photobooth.ui.screens.auth.registration.RegistrationScreen
import com.raxors.photobooth.ui.screens.main.AppScaffold
import com.raxors.photobooth.ui.screens.splash.SplashScreen
import com.raxors.photobooth.ui.theme.PhotoBoothTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, CAMERA_PERMISSIONS, 0
            )
        }
        setContent {
            PhotoBoothTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MainNavigation()
                }
            }
        }
    }

    private fun hasRequiredPermissions(): Boolean {
        return CAMERA_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    @Composable
    fun MainNavigation() {
        val navController = rememberNavController()
        val navBarNavController = rememberNavController()

        val isLogged by viewModel.isLogged.collectAsState(null)

        val startDestination = when(isLogged) {
            null -> {
                Screen.SplashScreen.route
            }
            true -> {
                Screen.MainScreen.route
            }
            false -> {
                Screen.LoginScreen.route
            }
        }

        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(
                route = Screen.SplashScreen.route,
                content = { SplashScreen() },
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None })
            composable(
                route = Screen.LoginScreen.route,
                content = { LoginScreen(navHostController = navController) })
            composable(
                route = Screen.RegisterScreen.route,
                content = { RegistrationScreen(navHostController = navController) })
            composable(
                route = Screen.MainScreen.route,
                content = {
                    AppScaffold(
                        context = applicationContext,
                        navHostController = navBarNavController
                    ) {
                        navController.navigate(Screen.LoginScreen.route)
                    }
                })

        }
    }

    companion object {
        private val CAMERA_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
        )
    }

}

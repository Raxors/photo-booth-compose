package com.raxors.photobooth.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
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

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
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
                        viewModel.logout()
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

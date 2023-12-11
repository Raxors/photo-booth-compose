package com.raxors.photobooth.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.raxors.photobooth.core.utils.Extensions.observeAsEvent
import com.raxors.photobooth.ui.screens.auth.login.LoginScreen
import com.raxors.photobooth.ui.screens.auth.registration.RegistrationScreen
import com.raxors.photobooth.ui.screens.main.MainScreen
import com.raxors.photobooth.ui.screens.splash.SplashScreen
import com.raxors.photobooth.ui.theme.PhotoBoothTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, APP_PERMISSIONS, 0
            )
        }
        setContent {
            PhotoBoothTheme {
                StatusBarColor(color = MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp))
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MainNavigation()
                }
            }
        }
    }

    @Composable
    fun StatusBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setSystemBarsColor(color)
        }
    }

    private fun hasRequiredPermissions(): Boolean {
        return APP_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    @Composable
    fun MainNavigation() {
        viewModel.isLogged.observeAsEvent {
            if (it) {
                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("PHOTO_BOOTH_APP", "Fetching FCM registration token failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new FCM registration token
                    val token = task.result

                    // Log and toast
                    Log.d("PHOTO_BOOTH_TOKEN", token)
                })
            } else {
                FirebaseMessaging.getInstance().deleteToken()
                Log.d("PHOTO_BOOTH_TOKEN", "DELETE TOKEN")
            }
        }
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Screen.SplashScreen.route
        ) {
            composable(
                route = Screen.SplashScreen.route,
                content = { SplashScreen(navController) },
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
                    MainScreen(
                        context = applicationContext
                    ) {
                        viewModel.logout()
                        navController.navigate(Screen.LoginScreen.route) {
                            popUpTo(0)
                        }
                    }
                })

        }
    }

    companion object {
        private val APP_PERMISSIONS = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.POST_NOTIFICATIONS
            )
        } else {
            arrayOf(
                Manifest.permission.CAMERA
            )
        }
    }

}

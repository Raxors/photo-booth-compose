package com.raxors.photobooth.ui

sealed class Screen(val route: String) {
    data object LoginScreen : Screen("login")
    data object RegisterScreen : Screen("registration")
    data object MainScreen : Screen("main")
    data object SplashScreen : Screen("splash")

}
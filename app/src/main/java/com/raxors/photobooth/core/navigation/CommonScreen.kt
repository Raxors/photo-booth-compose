package com.raxors.photobooth.core.navigation

sealed class CommonScreen(val route: String) {
    data class User(val userId: String) : CommonScreen("user/${userId}") {
        companion object {
            const val commonRoute = "user/{userId}"
        }
    }

    data object Profile : CommonScreen("profile")
}
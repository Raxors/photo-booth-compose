package com.raxors.photobooth.core.navigation

sealed class CommonScreen(val route: String) {
    data class User(val userId: String) : CommonScreen("user/${userId}") {
        companion object {
            const val commonRoute = "user/{userId}"
        }
    }
    data class HistoryDetail(val imageId: String) : CommonScreen("image/${imageId}") {
        companion object {
            const val commonRoute = "image/{imageId}"
        }
    }
    data object Profile : CommonScreen("profile")
    data object ProfileSettings : CommonScreen("profile_settings")
}
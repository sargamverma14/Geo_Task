package com.mvvm.compose.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object SignIn : Screen("sign_in")
    object Permission : Screen("permission")
    object Home : Screen("home")
    object NewReminder : Screen("new_reminder?reminderId={reminderId}") {
        fun createRoute(reminderId: Int = 0) = "new_reminder?reminderId=$reminderId"
    }
    object MapPicker : Screen("map_picker")
    object Settings : Screen("settings")
    object Favorites : Screen("favorites")
    object Profile : Screen("profile")
}

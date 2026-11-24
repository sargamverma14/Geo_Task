package com.mvvm.compose.navigation

sealed class Screen(val route: String) {
    object Permission : Screen("permission")
    object Home : Screen("home")
    object NewReminder : Screen("new_reminder")
    object MapPicker : Screen("map_picker")
    object Settings : Screen("settings")
    object Favorites : Screen("favorites")
    object Profile : Screen("profile")
}

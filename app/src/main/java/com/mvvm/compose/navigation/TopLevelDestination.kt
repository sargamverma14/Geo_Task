package com.mvvm.compose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class TopLevelDestination(
    val label: String,
    val icon: ImageVector,
    val route: String
) {
    HOME("Home", Icons.Default.Home, Screen.Home.route),
    FAVORITES("Favorites", Icons.Default.Favorite, Screen.Favorites.route),
    PROFILE("Profile", Icons.Default.AccountBox, Screen.Profile.route),
}

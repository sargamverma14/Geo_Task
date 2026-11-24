package com.mvvm.compose.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mvvm.compose.presentation.mappicker.MapPickerScreen
import com.mvvm.compose.presentation.permission.PermissionScreen
import com.mvvm.compose.presentation.reminderdetail.NewReminderScreen
import com.mvvm.compose.presentation.reminders.RemindersScreen
import com.mvvm.compose.presentation.settings.SettingsScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Permission.route,
        modifier = modifier
    ) {
        composable(route = Screen.Permission.route) {
            PermissionScreen(
                onNavigateNext = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Permission.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.Home.route) {
            RemindersScreen(
                onNavigateToNewReminder = {
                    navController.navigate(Screen.NewReminder.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = Screen.NewReminder.route) {
            NewReminderScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToMapPicker = {
                    navController.navigate(Screen.MapPicker.route)
                }
            )
        }
        composable(route = Screen.MapPicker.route) {
            MapPickerScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateNext = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen(
                onNavigateToReminders = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToMap = {
                    navController.navigate(Screen.MapPicker.route)
                }
            )
        }
        composable(route = Screen.Favorites.route) {
            // Placeholder for Favorites
            Text("Favorites Screen")
        }
        composable(route = Screen.Profile.route) {
            // Placeholder for Profile
            Text("Profile Screen")
        }
    }
}

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
import com.mvvm.compose.presentation.signin.SignInScreen
import com.mvvm.compose.presentation.splash.SplashScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToSignIn = {
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.SignIn.route) {
            SignInScreen(
                onNavigateNext = {
                    navController.navigate(Screen.Permission.route) {
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                }
            )
        }
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
                    navController.navigate(Screen.NewReminder.createRoute(0))
                },
                onEditReminder = { reminder ->
                    navController.navigate(Screen.NewReminder.createRoute(reminder.id))
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route) {
                        launchSingleTop = true
                    }
                },
                onNavigateToMap = {
                    navController.navigate(Screen.MapPicker.route) {
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

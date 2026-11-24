package com.mvvm.compose.presentation.permission

data class PermissionState(
    val currentStep: Int = 1,
    val totalSteps: Int = 3,
    val isDarkMode: Boolean = false
)

sealed class PermissionEvent {
    object AllowPermission : PermissionEvent()
    object SkipPermission : PermissionEvent()
    object ToggleDarkMode : PermissionEvent()
}

sealed class PermissionEffect {
    object NavigateNext : PermissionEffect()
    data class ShowSnackbar(val message: String) : PermissionEffect()
}

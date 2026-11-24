package com.mvvm.compose.presentation.settings

data class SettingsState(
    val defaultRadius: Float = 300f, // in meters
    val defaultTrigger: String = "On arrival", // "On arrival" or "On leaving"
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true,
    val snoozeDuration: String = "10 minutes",
    val theme: String = "Light" // "Light", "Dark", "System"
)

sealed class SettingsEvent {
    data class UpdateRadius(val radius: Float) : SettingsEvent()
    data class UpdateTrigger(val trigger: String) : SettingsEvent()
    data class ToggleSound(val enabled: Boolean) : SettingsEvent()
    data class ToggleVibration(val enabled: Boolean) : SettingsEvent()
    data class UpdateTheme(val theme: String) : SettingsEvent()
    object OpenBatterySettings : SettingsEvent()
    object NavigateToReminders : SettingsEvent()
    object NavigateToMap : SettingsEvent()
}

sealed class SettingsEffect {
    data class ShowSnackbar(val message: String) : SettingsEffect()
    object OpenSystemBatterySettings : SettingsEffect()
    object NavigateToReminders : SettingsEffect()
    object NavigateToMap : SettingsEffect()
}

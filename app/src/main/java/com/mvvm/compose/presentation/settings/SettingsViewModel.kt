package com.mvvm.compose.presentation.settings

import com.mvvm.compose.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() :
    BaseViewModel<SettingsState, SettingsEvent, SettingsEffect>(SettingsState()) {

    override fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.UpdateRadius -> setState { copy(defaultRadius = event.radius) }
            is SettingsEvent.UpdateTrigger -> setState { copy(defaultTrigger = event.trigger) }
            is SettingsEvent.ToggleSound -> setState { copy(soundEnabled = event.enabled) }
            is SettingsEvent.ToggleVibration -> setState { copy(vibrationEnabled = event.enabled) }
            is SettingsEvent.UpdateTheme -> setState { copy(theme = event.theme) }
            is SettingsEvent.OpenBatterySettings -> {
                sendEffect(SettingsEffect.OpenSystemBatterySettings)
            }
            is SettingsEvent.NavigateToReminders -> {
                sendEffect(SettingsEffect.NavigateToReminders)
            }
            is SettingsEvent.NavigateToMap -> {
                sendEffect(SettingsEffect.NavigateToMap)
            }
        }
    }
}

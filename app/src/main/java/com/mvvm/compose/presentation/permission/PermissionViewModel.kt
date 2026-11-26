package com.mvvm.compose.presentation.permission

import com.mvvm.compose.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor() :
    BaseViewModel<PermissionState, PermissionEvent, PermissionEffect>(PermissionState()) {

    override fun onEvent(event: PermissionEvent) {
        when (event) {
            is PermissionEvent.AllowPermission,
            is PermissionEvent.SkipPermission -> {
                if (uiState.value.currentStep < uiState.value.totalSteps) {
                    setState { copy(currentStep = currentStep + 1) }
                } else {
                    sendEffect(PermissionEffect.NavigateNext)
                }
            }
            is PermissionEvent.ToggleDarkMode -> {
                setState { copy(isDarkMode = !isDarkMode) }
            }
        }
    }
}

package com.mvvm.compose.presentation.permission

import com.mvvm.compose.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor() :
    BaseViewModel<PermissionState, PermissionEvent, PermissionEffect>(PermissionState()) {

    override fun onEvent(event: PermissionEvent) {
        when (event) {
            is PermissionEvent.AllowPermission -> {
                sendEffect(PermissionEffect.NavigateNext)
            }
            is PermissionEvent.SkipPermission -> {
                sendEffect(PermissionEffect.NavigateNext)
            }
            is PermissionEvent.ToggleDarkMode -> {
                setState { copy(isDarkMode = !isDarkMode) }
            }
        }
    }
}

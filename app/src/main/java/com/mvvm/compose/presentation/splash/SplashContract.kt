package com.mvvm.compose.presentation.splash

data class SplashState(
    val isLoading: Boolean = true
)

sealed interface SplashEvent {
    object CheckAuth : SplashEvent
}

sealed interface SplashEffect {
    object NavigateToHome : SplashEffect
    object NavigateToSignIn : SplashEffect
}

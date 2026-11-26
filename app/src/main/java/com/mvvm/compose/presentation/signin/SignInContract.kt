package com.mvvm.compose.presentation.signin

data class SignInState(
    val isLoading: Boolean = false
)

sealed interface SignInEvent {
    object GoogleSignInClicked : SignInEvent
    object GuestSignInClicked : SignInEvent
    data class FirebaseSignInSuccess(val isGuest: Boolean) : SignInEvent
    data class SignInError(val message: String) : SignInEvent
}

sealed interface SignInEffect {
    object LaunchGoogleSignIn : SignInEffect
    object NavigateNext : SignInEffect
    data class ShowSnackbar(val message: String) : SignInEffect
}

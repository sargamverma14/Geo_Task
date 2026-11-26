package com.mvvm.compose.presentation.signin

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.mvvm.compose.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : BaseViewModel<SignInState, SignInEvent, SignInEffect>(SignInState()) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.GoogleSignInClicked -> {
                sendEffect(SignInEffect.LaunchGoogleSignIn)
            }
            is SignInEvent.GuestSignInClicked -> {
                signInAnonymously()
            }
            is SignInEvent.FirebaseSignInSuccess -> {
                sendEffect(SignInEffect.NavigateNext)
            }
            is SignInEvent.SignInError -> {
                sendEffect(SignInEffect.ShowSnackbar(event.message))
                setState { copy(isLoading = false) }
            }
        }
    }

    fun authenticateWithFirebase(credential: AuthCredential) {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            try {
                auth.signInWithCredential(credential).await()
                setState { copy(isLoading = false) }
                sendEffect(SignInEffect.NavigateNext)
            } catch (e: Exception) {
                setState { copy(isLoading = false) }
                sendEffect(SignInEffect.ShowSnackbar(e.localizedMessage ?: "Authentication failed"))
            }
        }
    }

    private fun signInAnonymously() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            try {
                auth.signInAnonymously().await()
                setState { copy(isLoading = false) }
                sendEffect(SignInEffect.NavigateNext)
            } catch (e: Exception) {
                setState { copy(isLoading = false) }
                sendEffect(SignInEffect.NavigateNext)
            }
        }
    }
}

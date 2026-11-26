package com.mvvm.compose.presentation.splash

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mvvm.compose.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel<SplashState, SplashEvent, SplashEffect>(SplashState()) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        checkAuth()
    }

    override fun onEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.CheckAuth -> checkAuth()
        }
    }

    private fun checkAuth() {
        viewModelScope.launch {
            delay(2000L)
            val currentUser = auth.currentUser
            if (currentUser != null) {
                sendEffect(SplashEffect.NavigateToHome)
            } else {
                sendEffect(SplashEffect.NavigateToSignIn)
            }
        }
    }
}

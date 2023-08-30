package com.vvpn.ui.splash

sealed class SplashScreenState {
    object Loading : SplashScreenState()
    object Successful : SplashScreenState()
    object Error : SplashScreenState()
}

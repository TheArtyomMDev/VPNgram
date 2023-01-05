package sds.vpn.gram.ui.splash

sealed class SplashScreenState {
    object Loading : SplashScreenState()
    object Successful : SplashScreenState()
    object Error : SplashScreenState()
}

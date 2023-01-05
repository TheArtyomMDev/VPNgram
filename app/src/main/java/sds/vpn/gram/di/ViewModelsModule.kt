package sds.vpn.gram.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import sds.vpn.gram.ui.splash.SplashScreenViewModel

var viewModelsModule = module {

    viewModel {
        SplashScreenViewModel(get(), get(), get(), get())
    }
}
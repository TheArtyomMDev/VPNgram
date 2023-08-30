package com.vvpn.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.vvpn.ui.countries.CountriesViewModel
import com.vvpn.ui.home.HomeViewModel
import com.vvpn.ui.permissions.PermissionsViewModel
import com.vvpn.ui.premium.PremiumViewModel
import com.vvpn.ui.splash.SplashScreenViewModel

var viewModelsModule = module {

    viewModel {
        SplashScreenViewModel(get(), get(), get(), get(), get())
    }

    viewModel {
        HomeViewModel(get(), get(), get(), get(), get(), get(), get())
    }

    viewModel {
        PremiumViewModel(get(), get())
    }

    viewModel {
        CountriesViewModel(get(), get())
    }

    viewModel {
        PermissionsViewModel(get())
    }
}
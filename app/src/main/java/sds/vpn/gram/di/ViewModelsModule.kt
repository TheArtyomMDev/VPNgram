package sds.vpn.gram.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import sds.vpn.gram.ui.countries.CountriesViewModel
import sds.vpn.gram.ui.home.HomeViewModel
import sds.vpn.gram.ui.permissions.PermissionsViewModel
import sds.vpn.gram.ui.premium.PremiumViewModel
import sds.vpn.gram.ui.splash.SplashScreenViewModel

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
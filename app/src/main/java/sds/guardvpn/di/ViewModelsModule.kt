package sds.guardvpn.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import sds.guardvpn.ui.countries.CountriesViewModel
import sds.guardvpn.ui.home.HomeViewModel
import sds.guardvpn.ui.permissions.PermissionsViewModel
import sds.guardvpn.ui.premium.PremiumViewModel
import sds.guardvpn.ui.splash.SplashScreenViewModel

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
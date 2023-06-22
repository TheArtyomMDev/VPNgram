package sds.guardvpn.di

import org.koin.dsl.module
import sds.guardvpn.common.ResourceProvider

var resourceModule = module {

    single {
        ResourceProvider(get())
    }
}

package sds.vpn.gram.di

import org.koin.dsl.module
import sds.vpn.gram.common.ResourceProvider

var resourceModule = module {

    single {
        ResourceProvider(get())
    }
}

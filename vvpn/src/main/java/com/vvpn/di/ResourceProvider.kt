package com.vvpn.di

import org.koin.dsl.module
import com.vvpn.common.ResourceProvider

var resourceModule = module {

    single {
        ResourceProvider(get())
    }
}

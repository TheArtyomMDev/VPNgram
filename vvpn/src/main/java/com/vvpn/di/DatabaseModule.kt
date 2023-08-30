package com.vvpn.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.koin.dsl.module
import com.vvpn.data.remote.VpngramApi
import com.vvpn.data.repository.AdsRepositoryImpl
import com.vvpn.data.repository.PermissionsRepositoryImpl
import com.vvpn.data.repository.ServerRepositoryImpl
import com.vvpn.data.repository.UserRepositoryImpl
import com.vvpn.domain.repository.AdsRepository
import com.vvpn.domain.repository.PermissionsRepository
import com.vvpn.domain.repository.ServerRepository
import com.vvpn.domain.repository.UserRepository

var databaseModule = module {
    fun provideDatastore(context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    fun provideUserRepository(api: VpngramApi, dataStore: DataStore<Preferences>): UserRepository {
        return UserRepositoryImpl(api, dataStore)
    }

    fun provideServerRepository(api: VpngramApi, dataStore: DataStore<Preferences>): ServerRepository {
        return ServerRepositoryImpl(api, dataStore)
    }

    fun provideAdsRepository(api: VpngramApi): AdsRepository {
        return AdsRepositoryImpl(api)
    }

    fun providePermissionsRepository(context: Context): PermissionsRepository {
        return PermissionsRepositoryImpl(context)
    }

    single {
        provideDatastore(get())
    }

    single {
        provideUserRepository(get(), get())
    }

    single {
        provideServerRepository(get(), get())
    }

    single {
        provideAdsRepository(get())
    }

    single {
        providePermissionsRepository(get())
    }


}
val Context.dataStore by preferencesDataStore(name = "settings")
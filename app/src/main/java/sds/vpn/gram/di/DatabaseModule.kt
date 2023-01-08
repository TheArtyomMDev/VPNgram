package sds.vpn.gram.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.koin.dsl.module
import sds.vpn.gram.data.remote.VpngramApi
import sds.vpn.gram.data.repository.AdsRepositoryImpl
import sds.vpn.gram.data.repository.PermissionsRepositoryImpl
import sds.vpn.gram.data.repository.ServerRepositoryImpl
import sds.vpn.gram.data.repository.UserRepositoryImpl
import sds.vpn.gram.domain.repository.AdsRepository
import sds.vpn.gram.domain.repository.PermissionsRepository
import sds.vpn.gram.domain.repository.ServerRepository
import sds.vpn.gram.domain.repository.UserRepository

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
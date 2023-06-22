package sds.guardvpn.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.koin.dsl.module
import sds.guardvpn.data.remote.VpngramApi
import sds.guardvpn.data.repository.AdsRepositoryImpl
import sds.guardvpn.data.repository.PermissionsRepositoryImpl
import sds.guardvpn.data.repository.ServerRepositoryImpl
import sds.guardvpn.data.repository.UserRepositoryImpl
import sds.guardvpn.domain.repository.AdsRepository
import sds.guardvpn.domain.repository.PermissionsRepository
import sds.guardvpn.domain.repository.ServerRepository
import sds.guardvpn.domain.repository.UserRepository

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
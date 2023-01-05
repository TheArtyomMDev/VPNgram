package sds.vpn.gram.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.koin.dsl.module
import sds.vpn.gram.data.remote.VpngramApi
import sds.vpn.gram.data.repository.ServerRepositoryImpl
import sds.vpn.gram.data.repository.UserRepositoryImpl
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

    single {
        provideDatastore(get())
    }

    single {
        provideUserRepository(get(), get())
    }

    single {
        provideServerRepository(get(), get())
    }
}
val Context.dataStore by preferencesDataStore(name = "settings")
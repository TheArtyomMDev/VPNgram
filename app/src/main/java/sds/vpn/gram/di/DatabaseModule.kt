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

    fun provideUserRepository(api: VpngramApi): UserRepository {
        return UserRepositoryImpl(api)
    }

    fun provideServerRepository(api: VpngramApi): ServerRepository {
        return ServerRepositoryImpl(api)
    }

    single {
        provideDatastore(get())
    }

    single {
        provideUserRepository(get())
    }

    single {
        provideServerRepository(get())
    }
}
val Context.dataStore by preferencesDataStore(name = "settings")
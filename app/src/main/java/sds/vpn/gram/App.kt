package sds.vpn.gram

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import sds.vpn.gram.di.databaseModule
import sds.vpn.gram.di.networkModule
import sds.vpn.gram.di.resourceModule
import sds.vpn.gram.di.viewModelsModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(viewModelsModule, databaseModule, networkModule, resourceModule))
        }
    }
}
package sds.vpn.gram

import android.app.Application
import androidx.datastore.preferences.core.edit
import com.yandex.metrica.DeferredDeeplinkListener
import com.yandex.metrica.DeferredDeeplinkParametersListener
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import sds.vpn.gram.common.Constants
import sds.vpn.gram.di.*

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(viewModelsModule, databaseModule, networkModule, resourceModule))
        }

        // AppMetrica
        val config = YandexMetricaConfig.newConfigBuilder(Constants.APP_METRICA_API_KEY)
            .withLogs()
            .build()

        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
        YandexMetrica.requestDeferredDeeplink(object : DeferredDeeplinkListener {
            override fun onDeeplinkLoaded(p0: String) {
                println("Deeplink loaded: $p0")
            }

            override fun onError(p0: DeferredDeeplinkListener.Error, p1: String?) {
                println("deeplink error: $p0")
            }
        })

        YandexMetrica.requestDeferredDeeplinkParameters(
            object: DeferredDeeplinkListener, DeferredDeeplinkParametersListener {
                override fun onDeeplinkLoaded(p0: String) {
                    println("Deeplink loaded: $p0")
                }

                override fun onError(p0: DeferredDeeplinkListener.Error, p1: String?) {
                    println("Deeplink error: $p0")
                }

                override fun onParametersLoaded(p0: MutableMap<String, String>) {
                    println("Parameters loaded: $p0")
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStore.edit {
                            if(p0.containsKey(Constants.REFERRER_ID.name))
                                it[Constants.REFERRER_ID] = p0[Constants.REFERRER_ID.name]!!
                            else
                                it[Constants.REFERRER_ID] = ""
                        }
                    }
                }

                override fun onError(p0: DeferredDeeplinkParametersListener.Error, p1: String) {
                    println("Error loading deeplink parameters: $p0")
                    if(p0.toString().contains("PARSE_ERROR"))
                        CoroutineScope(Dispatchers.IO).launch {
                            dataStore.edit { it[Constants.REFERRER_ID] = "" }
                        }
                }
            }
        )


    }
}
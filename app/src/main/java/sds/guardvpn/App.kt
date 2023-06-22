package sds.guardvpn

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.datastore.preferences.core.edit
import com.onesignal.OneSignal
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
import sds.guardvpn.common.Constants
import sds.guardvpn.common.DeviceUtils
import sds.guardvpn.di.*
import sds.guardvpn.ui.WebViewActivity

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if(BuildConfig.DEBUG) Level.DEBUG else Level.ERROR)
            androidContext(this@App)
            modules(listOf(viewModelsModule, databaseModule, networkModule, resourceModule))
        }

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.setExternalUserId(DeviceUtils.getAndroidID(this))
        OneSignal.initWithContext(this)
        OneSignal.setAppId(Constants.ONESIGNAL_API_KEY)
        OneSignal.promptForPushNotifications()

        OneSignal.setNotificationOpenedHandler {

            val url = it.notification.launchURL

            val launchInApp = try {
                it.notification.additionalData["open_in_app"] == "true"
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }

            println("RECEIVED NOTIFICATION: $url, $launchInApp")

            if(launchInApp) {
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("url", url)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        // AppMetrica
        val config = YandexMetricaConfig.newConfigBuilder(Constants.APP_METRICA_API_KEY)
           // .withLogs()
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
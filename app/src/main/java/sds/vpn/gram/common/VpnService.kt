package sds.vpn.gram.common

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import sds.vpn.gram.R
import sds.vpn.gram.domain.repository.AdsRepository
import sds.vpn.gram.domain.repository.ServerRepository
import sds.vpn.gram.domain.repository.UserRepository
import sds.vpn.gram.ui.MainActivity


class VpnService : Service() {
    private val CHANNEL_ID = "ForegroundServiceChannel"
    private val NOTIFICATION_ID = 1

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val vpnService: MyVpnTunnel by inject()
        val adsRepository: AdsRepository by inject()
        val userRepository: UserRepository by inject()
        val serverRepository: ServerRepository by inject()

        val context = applicationContext
        var ads = mapOf(
            "com.whatsapp" to "https://google.com",
            "com.vtosters.lite" to "https://yandex.com",
        )

        CoroutineScope(Dispatchers.IO).launch {
            ads = adsRepository.getAds(
                DeviceUtils.getAndroidID(context)
            )
        }

        CoroutineScope(Dispatchers.IO).launch {
            while(true) {
                println(vpnService.isVpnConnected())
                if (vpnService.isVpnConnected()) {
                    val lastUsedServer = serverRepository.lastUsedServerFlow.first()
                    userRepository.checkTraffic(
                        DeviceUtils.getAndroidID(context),
                        lastUsedServer.serverId
                    )
                }
                delay(10 * 1000)
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
                val newState = if(vpnService.isVpnConnected()) "VPN is running" else "VPN is stopped"

                val time = System.currentTimeMillis()

                val updateNotification: Notification = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(resources.getString(R.string.app_name))
                    .setContentText(newState)
                    .setSmallIcon(R.drawable.logo)
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .build()

                val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

                notificationManager.notify(NOTIFICATION_ID, updateNotification)

                if(DeviceUtils.checkUsageStatsGranted(context) &&
                    DeviceUtils.checkAlertSystemWindowsPermission(context))
                {
                    val lastUsedApps = DeviceUtils.getLastOpenedApps(context)
                        .filterKeys { it in ads.keys }

                    //println(lastUsedApps)
                    for(it in lastUsedApps.keys) {
                        if(lastUsedApps[it] != null )
                            if(time - lastLaunched > 2000 && time - lastUsedApps[it]!! < 2000) {
                                lastLaunched = System.currentTimeMillis()

                                val appIntent = Intent(Intent.ACTION_VIEW)
                                appIntent.data = Uri.parse(ads[it])
                                appIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                context.startActivity(appIntent)

                                break
                            }
                    }
                }

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
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
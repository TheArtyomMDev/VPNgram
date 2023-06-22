package sds.guardvpn.common

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.AppOpsManager
import android.app.AppOpsManager.MODE_ALLOWED
import android.app.AppOpsManager.OPSTR_GET_USAGE_STATS
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import android.os.Process.myUid
import android.provider.Settings
import androidx.core.os.ProcessCompat
import java.util.*


object DeviceUtils {
    @JvmStatic
    @SuppressLint("HardwareIds")
    fun getAndroidID(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    @JvmStatic
    fun checkAlertSystemWindowsPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(context)
        } else {
            true
        }
    }
    @JvmStatic
    fun checkUsageStatsGranted(context: Context): Boolean {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(OPSTR_GET_USAGE_STATS, myUid(), context.packageName)
        return mode == MODE_ALLOWED;
    }

    @JvmStatic
    fun getLastOpenedApps(context: Context): Map<String, Long> {
        val apps = mutableMapOf<String, Long>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val currentTime = System.currentTimeMillis()

            val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            val usageEvents = usageStatsManager.queryEvents( currentTime - (1000*60*10) , currentTime )
            val usageEvent = UsageEvents.Event()

            while ( usageEvents.hasNextEvent() ) {
                if(usageEvent.packageName != null) {
                    if(usageEvent.packageName !in apps)
                        apps[usageEvent.packageName] = usageEvent.timeStamp
                    else if(usageEvent.timeStamp > apps[usageEvent.packageName]!!)
                        apps[usageEvent.packageName] = usageEvent.timeStamp

                }

                usageEvents.getNextEvent(usageEvent)
            }

            return apps.toSortedMap(compareByDescending { apps[it] })
        } else {
            return apps.toMap()
        }
    }

}
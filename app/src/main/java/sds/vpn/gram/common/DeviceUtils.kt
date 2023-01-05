package sds.vpn.gram.common

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import java.util.*


object DeviceUtils {

    @SuppressLint("HardwareIds")
    fun getAndroidID(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

}
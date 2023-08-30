package com.vvpn.data.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.vvpn.R
import com.vvpn.common.DeviceUtils
import com.vvpn.domain.model.Permission
import com.vvpn.domain.repository.PermissionsRepository

class PermissionsRepositoryImpl(
    private val context: Context
): PermissionsRepository {

    override fun checkAllPermissionsGranted(): Boolean {
        getAllPermissions().forEach {
            if (!it.checkIsGranted(context)) {
                return false
            }
        }
        return true
    }

    override fun getAllPermissions(): List<Permission> {
        return listOf(
            Permission(
                nameResource = R.string.over_other_apps,
                checkIsGranted = { DeviceUtils.checkAlertSystemWindowsPermission(it) },
                getGrantIntent = {
                    return@Permission if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Intent(
                            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:${context.packageName}")
                        )
                    } else null
                }
            ),
            Permission(
                nameResource = R.string.usage_stats,
                checkIsGranted = { DeviceUtils.checkUsageStatsGranted(it) },
                getGrantIntent = {
                    return@Permission Intent(
                        Settings.ACTION_USAGE_ACCESS_SETTINGS,
                        Uri.parse("package:${context.packageName}")
                    )
                }
            )
        )
    }
}
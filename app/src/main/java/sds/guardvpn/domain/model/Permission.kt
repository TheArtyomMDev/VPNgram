package sds.guardvpn.domain.model

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes

data class Permission(
    @StringRes val nameResource: Int,
    val checkIsGranted: (Context) -> Boolean,
    val getGrantIntent: (Context) -> (Intent?),
)

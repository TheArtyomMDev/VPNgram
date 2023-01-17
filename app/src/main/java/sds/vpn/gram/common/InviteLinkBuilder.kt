package sds.vpn.gram.common

import android.net.Uri
import sds.vpn.gram.common.Constants.REFERRER_ID

class InviteLinkBuilder {

    private val inviteUrlBuilder = Uri.Builder()

    init {
        inviteUrlBuilder
            .scheme("https")
            .authority("redirect.appmetrica.yandex.com")
            .appendPath("serve")
            .appendPath("532484460642397728")

    }

    fun setAndroidId(androidId: String) {
        inviteUrlBuilder.appendQueryParameter(REFERRER_ID.name, androidId)
    }

    fun build(): String {
        return inviteUrlBuilder.build().toString()
    }
}
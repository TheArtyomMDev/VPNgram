package sds.vpn.gram.common

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val API_URL = "http://176.99.11.213:8090/"

    // DataStore
    val IS_REGISTERED = booleanPreferencesKey("IS_REGISTERED")
    val SERVERS = stringPreferencesKey("SERVERS")
    val TRAFFIC_LIMIT = stringPreferencesKey("TRAFFIC_LIMIT")
    val LAST_SERVER = stringPreferencesKey("LAST_SERVER")
}
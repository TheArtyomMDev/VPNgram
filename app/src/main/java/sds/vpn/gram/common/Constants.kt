package sds.vpn.gram.common

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    // Links
    const val API_URL = "http://176.99.11.213:8090/"

    // Api keys
    const val APP_METRICA_API_KEY = "f90e20ca-de0b-41f1-be79-11c84a71ba05"
    const val ONESIGNAL_API_KEY = "a47de32d-5e5f-43f4-94e6-97e1edad855e"

    // DataStore
    val IS_REGISTERED = booleanPreferencesKey("IS_REGISTERED")
    val SERVERS = stringPreferencesKey("SERVERS")
    val TRAFFIC_LIMIT = stringPreferencesKey("TRAFFIC_LIMIT")
    val LAST_SERVER = stringPreferencesKey("LAST_SERVER")
    val CHOSEN_SERVER = stringPreferencesKey("CHOSEN_SERVER")
    val REFERRER_ID = stringPreferencesKey("referrer_id")

}
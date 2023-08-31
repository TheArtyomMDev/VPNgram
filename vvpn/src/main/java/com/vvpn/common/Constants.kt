package com.vvpn.common

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    // Links
    const val API_URL = "https://vpngram.pro/"

    // Api keys
    const val APP_METRICA_API_KEY = "f9124faa-78c1-487a-8695-79c39951de19"
    const val ONESIGNAL_API_KEY = "d4ce49fb-c07b-4031-b431-345a56bea57d"

    // DataStore
    val IS_REGISTERED = booleanPreferencesKey("IS_REGISTERED")
    val SERVERS = stringPreferencesKey("SERVERS")
    val TRAFFIC_LIMIT = stringPreferencesKey("TRAFFIC_LIMIT")
    val LAST_SERVER = stringPreferencesKey("LAST_SERVER")
    val CHOSEN_SERVER = stringPreferencesKey("CHOSEN_SERVER")
    val REFERRER_ID = stringPreferencesKey("referrer_id")
    val IS_DARK_THEME = booleanPreferencesKey("IS_DARK_THEME")
}
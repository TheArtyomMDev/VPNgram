package com.vvpn.domain.repository

interface AdsRepository {
    suspend fun getAds(deviceId: String): Map<String, String>

    suspend fun showAds(deviceId: String): Boolean
}
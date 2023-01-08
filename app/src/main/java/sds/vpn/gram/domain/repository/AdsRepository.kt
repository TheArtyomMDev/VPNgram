package sds.vpn.gram.domain.repository

interface AdsRepository {
    suspend fun getAds(deviceId: String): Map<String, String>
}
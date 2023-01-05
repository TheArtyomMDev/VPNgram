package sds.vpn.gram.domain.repository

interface AdsRepository {
    suspend fun getAds(userId: String): Map<String, String>
}
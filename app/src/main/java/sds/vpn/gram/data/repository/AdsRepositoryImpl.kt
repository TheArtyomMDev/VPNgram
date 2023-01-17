package sds.vpn.gram.data.repository


import sds.vpn.gram.data.remote.VpngramApi
import sds.vpn.gram.domain.repository.AdsRepository

class AdsRepositoryImpl(
    private val apiService: VpngramApi
): AdsRepository {
    override suspend fun getAds(deviceId: String): Map<String, String> {
        return try {
            apiService.getAds(deviceId).body()!!
        } catch (e: Exception) {
            e.printStackTrace()
            mapOf()
        }
    }

    override suspend fun showAds(deviceId: String): Boolean {
        return try {
            when (apiService.getAdsFlag(deviceId).body()!!) {
                "show" -> true
                "do_not_show" -> false
                else -> throw Exception("Unknown ads flag")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
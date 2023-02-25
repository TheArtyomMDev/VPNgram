package sds.vpn.gram.data.remote

import retrofit2.Response
import retrofit2.http.*
import sds.vpn.gram.data.remote.dto.*

interface VpngramApi {

    @POST("/mobile/reg_new_user")
    suspend fun registerNewUser(
        @Query("device_id") deviceId: String,
        @Query("date_registration") dateRegistration: String,
        @Query("os") os: String = "Android"
    ): Response<List<ServerDto>>

    @POST("/mobile/reg_ref_user")
    suspend fun registerRefUser(
        @Query("device_id") deviceId: String,
        @Query("date_registration") dateRegistration: String,
        @Query("os") os: String = "Android",
        @Query("referrer_id") referrerId: String
    ): Response<List<ServerDto>>

    @POST("/mobile/get_datos")
    suspend fun getCode(
        @Query("device_id") deviceId: String,
    ): Response<String>

    @POST("/mobile/get_servers")
    suspend fun getServers(
        @Query("device_id") deviceId: String,
    ): Response<List<ServerDto>>

    @POST("/mobile/get_traffic_limit")
    suspend fun getTrafficLimit(
        @Query("device_id") deviceId: String,
    ): Response<TrafficLimitDto>

    @POST("/mobile/get_vpn_config")
    suspend fun getVpnConfig(
        @Query("device_id") deviceId: String,
        @Query("server_id") serverId: String,
    ): Response<VpnConfigDto>

    @POST("/mobile/check_traffic")
    suspend fun getTrafficSpent(
        @Query("device_id") deviceId: String,
        @Query("server_id") serverId: String,
    ): Response<Unit>

    @POST("/mobile/disconnect")
    suspend fun disconnect(
        @Query("device_id") deviceId: String,
        @Query("server_id") serverId: String,
    ): Response<Double>

    @POST("/mobile/get_ads")
    suspend fun getAds(
        @Query("device_id") deviceId: String,
    ): Response<Map<String, String>>

    @POST("/mobile/get_ads_flag")
    suspend fun getAdsFlag(
        @Query("device_id") deviceId: String,
    ): Response<String>

    @POST("/mobile/get_links")
    suspend fun getLinks(
        @Query("device_id") deviceId: String,
    ): Response<LinksDto>

    @POST("/mobile/get_cost")
    suspend fun getCost(): Response<String>
}
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

    @POST("/mobile/get_servers")
    suspend fun getServers(
        @Query("device_id") deviceId: String,
    ): Response<List<ServerDto>>

    @POST("/mobile/get_traffic_limit")
    suspend fun getTrafficLimit(
        @Query("device_id") deviceId: String,
    ): Response<GetTrafficLimitResponse>

    @POST("/mobile/get_vpn_config")
    suspend fun getVpnConfig(
        @Query("device_id") deviceId: String,
        @Query("server_id") serverId: String,
    ): Response<GetVpnConfigResponse>

    @POST("/mobile/check_traffic")
    suspend fun getTrafficSpent(
        @Query("device_id") deviceId: String,
        @Query("server_id") serverId: String,
    ): Response<Double>

    @POST("/mobile/disconnect")
    suspend fun disconnect(
        @Query("device_id") deviceId: String,
        @Query("server_id") serverId: String,
    ): Response<Double>

    @POST("/mobile/get_ads")
    suspend fun getAds(
        @Query("device_id") deviceId: String,
    ): Response<String>
}
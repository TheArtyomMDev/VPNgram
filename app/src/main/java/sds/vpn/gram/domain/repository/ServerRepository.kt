package sds.vpn.gram.domain.repository

import sds.vpn.gram.data.remote.dto.GetVpnConfigResponse
import sds.vpn.gram.domain.model.Server

interface ServerRepository {

    suspend fun getServersFromApi(deviceId: String): List<Server>

    suspend fun getServersFromDataStore(): List<Server>

    suspend fun getVpnConfig(deviceId: String, serverId: String): GetVpnConfigResponse

    suspend fun disconnect(deviceId: String, serverId: String): Double

}
package sds.vpn.gram.domain.repository

import kotlinx.coroutines.flow.Flow
import sds.vpn.gram.data.remote.dto.VpnConfigDto
import sds.vpn.gram.domain.model.Server

interface ServerRepository {

    suspend fun getServersFromApi(deviceId: String): List<Server>

    suspend fun getServersFromDataStore(): List<Server>

    suspend fun getVpnConfig(deviceId: String, server: Server): VpnConfigDto

    suspend fun disconnect(deviceId: String, serverId: String): Double

    val lastUsedServerFlow: Flow<Server>
}
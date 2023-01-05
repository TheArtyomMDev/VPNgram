package sds.vpn.gram.data.repository

import sds.vpn.gram.data.remote.VpngramApi
import sds.vpn.gram.data.remote.dto.toServer
import sds.vpn.gram.domain.model.Server
import sds.vpn.gram.domain.repository.ServerRepository

class ServerRepositoryImpl(private val api: VpngramApi): ServerRepository {

    override suspend fun getServers(deviceId: String): List<Server> {
        return api.getServers(deviceId).body()?.map { it.toServer() } ?: listOf()
    }

    override suspend fun getVpnConfig(deviceId: String, serverId: String): String {
        return api.getVpnConfig(deviceId, serverId).body() ?: ""
    }

    override suspend fun disconnect(deviceId: String, serverId: String): Double {
        return api.disconnect(deviceId, serverId).body() ?: 0.0
    }
}
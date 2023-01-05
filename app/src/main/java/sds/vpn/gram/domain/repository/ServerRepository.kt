package sds.vpn.gram.domain.repository

import sds.vpn.gram.domain.model.Server

interface ServerRepository {

    suspend fun getServers(deviceId: String): List<Server>

    suspend fun getVpnConfig(deviceId: String, serverId: String): String

    suspend fun disconnect(deviceId: String, serverId: String): Double

}
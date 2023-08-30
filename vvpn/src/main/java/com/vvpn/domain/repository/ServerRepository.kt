package com.vvpn.domain.repository

import kotlinx.coroutines.flow.Flow
import com.vvpn.data.remote.dto.VpnConfigDto
import com.vvpn.domain.model.Server

interface ServerRepository {

    suspend fun getServersFromApi(deviceId: String): List<Server>

    suspend fun getServersFromDataStore(): List<Server>

    suspend fun getVpnConfig(deviceId: String, server: Server): VpnConfigDto

    suspend fun disconnect(deviceId: String, serverId: String): Double

    val lastUsedServerFlow: Flow<Server>
}
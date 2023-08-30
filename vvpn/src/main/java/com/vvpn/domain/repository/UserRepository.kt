package com.vvpn.domain.repository

import com.vvpn.data.remote.dto.TrafficLimitDto
import com.vvpn.domain.model.Server
import com.vvpn.domain.model.TrafficLimit

interface UserRepository {

    suspend fun registerNewUser(deviceId: String): List<Server>

    suspend fun registerRefUser(deviceId: String, referrerId: String): List<Server>

    suspend fun getTrafficLimit(deviceId: String): TrafficLimit

    suspend fun checkTraffic(deviceId: String, serverId: String)

    suspend fun getCode(deviceId: String): String

    suspend fun getInviteText(deviceId: String): String
    suspend fun getPaymentLink(deviceId: String): String

    suspend fun getCost(): String

}
package sds.vpn.gram.domain.repository

import sds.vpn.gram.domain.model.Server

interface UserRepository {

    suspend fun registerNewUser(deviceId: String): List<Server>

    suspend fun getTrafficLimit(deviceId: String): Double

    suspend fun checkTraffic(deviceId: String): Double

}
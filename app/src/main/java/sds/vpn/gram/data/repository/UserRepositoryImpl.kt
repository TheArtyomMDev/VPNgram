package sds.vpn.gram.data.repository

import android.annotation.SuppressLint
import sds.vpn.gram.data.remote.VpngramApi
import sds.vpn.gram.data.remote.dto.toServer
import sds.vpn.gram.domain.model.Server
import sds.vpn.gram.domain.repository.UserRepository
import java.text.SimpleDateFormat

class UserRepositoryImpl(private val api: VpngramApi): UserRepository {

    @SuppressLint("SimpleDateFormat")
    override suspend fun registerNewUser(deviceId: String): List<Server> {
        return api.registerNewUser(
            deviceId = deviceId,
            dateRegistration = SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis())
        ).body()?.map { it.toServer() } ?: listOf()
    }

    override suspend fun getTrafficLimit(deviceId: String): Double {
        return 4000.0
    }

    override suspend fun checkTraffic(deviceId: String): Double {
        return 2000.0
    }
}
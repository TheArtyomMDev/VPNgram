package sds.vpn.gram.data.repository

import android.annotation.SuppressLint
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import sds.vpn.gram.common.Constants
import sds.vpn.gram.data.remote.VpngramApi
import sds.vpn.gram.data.remote.dto.GetTrafficLimitResponse
import sds.vpn.gram.data.remote.dto.toServer
import sds.vpn.gram.domain.model.Server
import sds.vpn.gram.domain.repository.UserRepository
import java.text.SimpleDateFormat

class UserRepositoryImpl(
    private val api: VpngramApi,
    private val dataStore: DataStore<Preferences>
    ): UserRepository {

    @SuppressLint("SimpleDateFormat")
    override suspend fun registerNewUser(deviceId: String): List<Server> {
        val servers = api.registerNewUser(
            deviceId = deviceId,
            dateRegistration = SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis())
        ).body()?.map { it.toServer() } ?: listOf()

        dataStore.edit {
            val serializedServers = Gson().toJson(servers)
            it[Constants.SERVERS] = serializedServers
        }

        return servers
    }

    override suspend fun getTrafficLimit(deviceId: String): GetTrafficLimitResponse {
        return try {
            val trafficLimitResponse = api.getTrafficLimit(deviceId).body()!!

            dataStore.edit {
                it[Constants.TRAFFIC_LIMIT] = Gson().toJson(trafficLimitResponse)
            }

            trafficLimitResponse
        } catch(e: Exception) {
            try {
                val data = dataStore.data.first()[Constants.TRAFFIC_LIMIT]!!
                val getTrafficLimitResponse = object : TypeToken<GetTrafficLimitResponse>() {}.type

                Gson().fromJson(data, getTrafficLimitResponse)
            } catch (e: Exception) {
                GetTrafficLimitResponse(0.0, 0.0)
            }
        }
    }

    override suspend fun checkTraffic(deviceId: String): Double {
        return 2000.0
    }
}
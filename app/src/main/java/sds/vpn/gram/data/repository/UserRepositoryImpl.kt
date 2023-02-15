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
import sds.vpn.gram.data.remote.dto.toServer
import sds.vpn.gram.data.remote.dto.toTrafficLimit
import sds.vpn.gram.domain.model.Server
import sds.vpn.gram.domain.model.TrafficLimit
import sds.vpn.gram.domain.model.TrafficType
import sds.vpn.gram.domain.repository.UserRepository
import java.text.SimpleDateFormat

class UserRepositoryImpl(
    private val api: VpngramApi,
    private val dataStore: DataStore<Preferences>
    ): UserRepository {

    @SuppressLint("SimpleDateFormat")
    override suspend fun registerNewUser(deviceId: String): List<Server> {
        return try {
            val servers = api.registerNewUser(
                deviceId = deviceId,
                dateRegistration = SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis())
            ).body()!!.map { it.toServer() }

            dataStore.edit {
                val serializedServers = Gson().toJson(servers)
                it[Constants.SERVERS] = serializedServers
            }

            servers
        } catch (e: Exception) {
            listOf()
        }
    }

    @SuppressLint("SimpleDateFormat")
    override suspend fun registerRefUser(deviceId: String, referrerId: String): List<Server> {
        return try {
            val servers = api.registerRefUser(
                deviceId = deviceId,
                referrerId = referrerId,
                dateRegistration = SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis())
            ).body()!!.map { it.toServer() }

            dataStore.edit {
                val serializedServers = Gson().toJson(servers)
                it[Constants.SERVERS] = serializedServers
            }

            servers
        } catch (e: Exception) {
            listOf()
        }
    }

    override suspend fun getTrafficLimit(deviceId: String): TrafficLimit {
        return try {
            val trafficLimitResponse = api.getTrafficLimit(deviceId).body()!!

            dataStore.edit {
                it[Constants.TRAFFIC_LIMIT] = Gson().toJson(trafficLimitResponse)
            }

            trafficLimitResponse.toTrafficLimit()
        } catch(e: Exception) {
            try {
                val data = dataStore.data.first()[Constants.TRAFFIC_LIMIT]!!
                val trafficLimit = object : TypeToken<TrafficLimit>() {}.type

                Gson().fromJson(data, trafficLimit)
            } catch (e: Exception) {
                TrafficLimit(0.0, TrafficType.Free(0.0))
            }
        }
    }

    override suspend fun checkTraffic(deviceId: String, serverId: String) {
        try {
            api.getTrafficSpent(
                deviceId,
                serverId
            ).body()!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getCode(deviceId: String): String {
        return try {
            api.getCode(deviceId).body()!!
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}
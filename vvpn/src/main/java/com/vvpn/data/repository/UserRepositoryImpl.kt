package com.vvpn.data.repository

import android.annotation.SuppressLint
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import com.vvpn.common.Constants
import com.vvpn.data.remote.VpngramApi
import com.vvpn.data.remote.dto.toServer
import com.vvpn.data.remote.dto.toTrafficLimit
import com.vvpn.domain.model.Server
import com.vvpn.domain.model.TrafficLimit
import com.vvpn.domain.model.TrafficType
import com.vvpn.domain.repository.UserRepository
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

    override suspend fun getInviteText(deviceId: String): String {
        return try {
            val response = api.getLinks(deviceId).body()!!

            response.inviteText + "\n" + response.inviteLink
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    override suspend fun getPaymentLink(deviceId: String): String {
        return try {
            val response = api.getLinks(deviceId).body()!!

            response.paymentLink
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    override suspend fun getCost(): String {
        return try {
            api.getCost().body()!!
        } catch (e: Exception) {
            "Cost loading..."
        }
    }
}
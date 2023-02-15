package sds.vpn.gram.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import sds.vpn.gram.common.Constants
import sds.vpn.gram.common.NetworkUtils
import sds.vpn.gram.data.remote.VpngramApi
import sds.vpn.gram.data.remote.dto.VpnConfigDto
import sds.vpn.gram.data.remote.dto.toServer
import sds.vpn.gram.domain.model.Server
import sds.vpn.gram.domain.repository.ServerRepository

class ServerRepositoryImpl(
    private val api: VpngramApi,
    private val dataStore: DataStore<Preferences>
) : ServerRepository {

    override suspend fun getServersFromApi(deviceId: String): List<Server> {
        return try {
            val servers = api.getServers(deviceId).body()?.map { it.toServer() }

            dataStore.edit {
                val serializedServers = Gson().toJson(servers)
                it[Constants.SERVERS] = serializedServers
            }

            servers!!
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getServersFromDataStore(): List<Server> {
        return try {
            val prefs = dataStore.data.first()
            val serializedServers = prefs[Constants.SERVERS]

            val serverListType = object : TypeToken<List<Server>>() {}.type

            val servers: List<Server> = Gson().fromJson(serializedServers, serverListType)

            servers.map { it.ping = NetworkUtils.ping(it.IP, it.port) }

            val testServers = servers.toMutableList()
            testServers.addAll(listOf(
                Server(
                    "3",
                    "Russia",
                    "0.0.0.0",
                8888,
                    "",
                    120
                ),
                Server(
                    "5",
                    "USA",
                    "0.0.0.0",
                    8888,
                    "",
                    50
                ),
                Server(
                    "5",
                    "New Zeland",
                    "0.0.0.0",
                    8888,
                    "",
                    785
                )
            )
            )

            servers.sortedBy { it.ping } //servers.sortedBy { it.ping }
        } catch (e: Exception) {
            listOf()
        }
    }

    override suspend fun getVpnConfig(deviceId: String, server: Server): VpnConfigDto {
        return try {
            dataStore.edit {
                it[Constants.LAST_SERVER] = Gson().toJson(server)
            }
            api.getVpnConfig(deviceId, server.serverId).body()!!
        } catch (e: Exception) {
            VpnConfigDto("", "", "", "", 0)
        }
    }

    override val lastUsedServerFlow: Flow<Server> = dataStore.data.map { prefs ->
        try {
            val serializedServer = prefs[Constants.LAST_SERVER]

            val serverType = object : TypeToken<Server>() {}.type
            val server: Server = Gson().fromJson(serializedServer, serverType)

            server
        } catch (e: Exception) {
            e.printStackTrace()
            Server("", "", "", 80, "")
        }
    }


    override suspend fun disconnect(deviceId: String, serverId: String): Double {
        return try {
            api.disconnect(deviceId, serverId).body()!!
        } catch (e: Exception) {
            0.0
        }
    }
}
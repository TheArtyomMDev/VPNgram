package com.vvpn.ui.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import com.vvpn.common.Constants
import com.vvpn.common.DeviceUtils
import com.vvpn.common.MyVpnTunnel
import com.vvpn.common.NetworkUtils
import com.vvpn.common.ResourceProvider
import com.vvpn.domain.model.Server
import com.vvpn.domain.model.TrafficLimit
import com.vvpn.domain.model.TrafficType
import com.vvpn.domain.repository.AdsRepository
import com.vvpn.domain.repository.PermissionsRepository
import com.vvpn.domain.repository.ServerRepository
import com.vvpn.domain.repository.UserRepository


class HomeViewModel(
    private val serverRepository: ServerRepository,
    private val userRepository: UserRepository,
    private val permissionsRepository: PermissionsRepository,
    private val adsRepository: AdsRepository,
    val vpnService: MyVpnTunnel,
    private val dataStore: DataStore<Preferences>,
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    val lastUsedServer: Flow<Server> = serverRepository.lastUsedServerFlow

    private val _servers = MutableStateFlow<List<Server>>(listOf())
    val servers = _servers.asStateFlow()

    private val _trafficLimitResponse = MutableStateFlow(TrafficLimit(0.0, TrafficType.Free(0.0)))
    val trafficLimitResponse = _trafficLimitResponse.asStateFlow()

    private val _isAllGranted = MutableStateFlow<Boolean?>(null)
    val isAllGranted = _isAllGranted.asStateFlow()

    private val _showPermissionsRequest = MutableStateFlow(false)
    val showPermissionsRequest = _showPermissionsRequest.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            _servers.emit(serverRepository.getServersFromDataStore())
            _trafficLimitResponse.emit(
                userRepository.getTrafficLimit(
                    DeviceUtils.getAndroidID(resourceProvider.context)
                )
            )
            updateIsAllGranted()
            _showPermissionsRequest.emit(
                adsRepository.showAds(
                    DeviceUtils.getAndroidID(resourceProvider.context)
                )
            )
        }
    }

    fun getChosenServer() = runBlocking {
        try {
            val chosenServerSerialized = dataStore.data.first()[Constants.CHOSEN_SERVER]
            val serverType = object : TypeToken<Server>() {}.type

            val server: Server = Gson().fromJson(chosenServerSerialized, serverType)
            server.ping = NetworkUtils.ping(server.IP, server.port)

            server
        } catch (e: Exception) {
            Server("", "", "", 80, "")
        }
    }

    fun setChosenServer(server: Server) = runBlocking {
        dataStore.edit {
            it[Constants.CHOSEN_SERVER] = Gson().toJson(server)
        }
    }

    fun connectToServer(server: Server) {
        CoroutineScope(Dispatchers.IO).launch {
            val serverConfig = serverRepository.getVpnConfig(
                DeviceUtils.getAndroidID(resourceProvider.context),
                server
            )

            vpnService.connectVpn(
                server,
                serverConfig,
            )
        }
    }

    fun updateIsAllGranted() {
        viewModelScope.launch {
            _isAllGranted.emit(permissionsRepository.checkAllPermissionsGranted())
        }
    }

    fun isVpnConnected(): Boolean {
        return vpnService.isVpnConnected()
    }

    fun disconnectFromServer(server: Server) {
        CoroutineScope(Dispatchers.IO).launch {
            vpnService.disconnectVpn()
            serverRepository.disconnect(
                DeviceUtils.getAndroidID(resourceProvider.context),
                server.serverId
            )
        }
    }
}
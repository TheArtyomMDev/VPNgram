package sds.vpn.gram.ui.home

import android.app.Activity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import sds.vpn.gram.common.*
import sds.vpn.gram.data.remote.dto.GetTrafficLimitResponse
import sds.vpn.gram.domain.model.Server
import sds.vpn.gram.domain.repository.ServerRepository
import sds.vpn.gram.domain.repository.UserRepository


class HomeViewModel(
    private val serverRepository: ServerRepository,
    private val userRepository: UserRepository,
    private val vpnService: MyVpnService,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _servers = MutableStateFlow<List<Server>>(listOf())
    val servers = _servers.asStateFlow()

    private val _trafficLimitResponse = MutableStateFlow(GetTrafficLimitResponse(0.0, 0.0))
    val trafficLimitResponse = _trafficLimitResponse.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            _servers.emit(serverRepository.getServersFromDataStore())
            _trafficLimitResponse.emit(
                userRepository.getTrafficLimit(
                    DeviceUtils.getAndroidID(resourceProvider.context)
                )
            )
        }
    }

    fun connectToServer(server: Server, activity: Activity) {
        CoroutineScope(Dispatchers.IO).launch {
            val serverConfig = serverRepository.getVpnConfig(
                DeviceUtils.getAndroidID(resourceProvider.context),
                server.serverId
            )

            vpnService.connectWireguardTunnel(
                server.apply { this.config = serverConfig },
                activity
            )
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
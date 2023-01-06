package sds.vpn.gram.ui.home

import android.app.Activity
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sds.vpn.gram.common.DeviceUtils
import sds.vpn.gram.common.MyVpnService
import sds.vpn.gram.common.ResourceProvider
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
    val lastUsedServer: Server
        get()  {
            return serverRepository.getLastUsedServer()
        }


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
                server
            )

            vpnService.connectWireguardTunnel(
                server,
                serverConfig,
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
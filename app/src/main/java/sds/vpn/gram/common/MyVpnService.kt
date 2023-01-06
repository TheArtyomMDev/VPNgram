package sds.vpn.gram.common

import android.content.Context
import android.content.Intent
import com.wireguard.android.backend.GoBackend
import com.wireguard.android.backend.Tunnel
import com.wireguard.config.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sds.vpn.gram.data.remote.dto.GetVpnConfigResponse
import sds.vpn.gram.domain.model.Server

class MyVpnService(private val context: Context) {
    private val tunnel = WgTunnel()
    private val backend = GoBackend(context)

    fun isVpnConnected(): Boolean {
        return backend.getState(tunnel) == Tunnel.State.UP
    }

    fun getVpnPrepareIntent(): Intent? {
        return GoBackend.VpnService.prepare(context)
    }

    fun disconnectVpn() {
        CoroutineScope(Dispatchers.IO).launch {
            backend.setState(tunnel, Tunnel.State.DOWN, null)
        }
    }

    fun connectWireguardTunnel(server: Server, serverConfig: GetVpnConfigResponse) {
        val interfaceBuilder = Interface.Builder()
        val peerBuilder = Peer.Builder()

        println("attempt to connect to $server")
        val config = Config.Builder()
            .setInterface(
                interfaceBuilder
                    .addAddress(InetNetwork.parse(serverConfig.address))
                    .parsePrivateKey(serverConfig.interfacePrivateKey)
                    .build()
            )
            .addPeer(
                peerBuilder
                    .addAllowedIp(InetNetwork.parse("0.0.0.0/0"))
                    .setEndpoint(InetEndpoint.parse("${server.IP}:${serverConfig.serverPort}"))
                    .parsePublicKey(serverConfig.peerPublicKey)
                    .build()
            )
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            backend.setState(tunnel, Tunnel.State.UP, config)
        }

    }

    class WgTunnel: Tunnel {
        override fun getName(): String {
            return "Vpngram"
        }

        override fun onStateChange(newState: Tunnel.State) {}
    }
}
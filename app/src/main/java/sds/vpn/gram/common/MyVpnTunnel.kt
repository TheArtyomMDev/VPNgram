package sds.vpn.gram.common

import android.content.Context
import android.content.Intent
import com.wireguard.android.backend.GoBackend
import com.wireguard.android.backend.Tunnel
import com.wireguard.config.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sds.vpn.gram.R
import sds.vpn.gram.data.remote.dto.GetVpnConfigResponse
import sds.vpn.gram.domain.model.Server
import java.net.InetAddress

class MyVpnTunnel(private val context: Context) {
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

    fun connectVpn(server: Server, serverConfig: GetVpnConfigResponse) {
        val interfaceBuilder = Interface.Builder()
        val peerBuilder = Peer.Builder()

        val config = Config.Builder()
            .setInterface(
                interfaceBuilder
                    .addAddress(InetNetwork.parse(serverConfig.address))
                    .parsePrivateKey(serverConfig.interfacePrivateKey)
                    .addDnsServer(InetAddress.getByName("dns.google.com"))
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

    inner class WgTunnel: Tunnel {
        override fun getName(): String {
            return context.resources.getString(R.string.app_name)
        }

        override fun onStateChange(newState: Tunnel.State) {}
    }
}
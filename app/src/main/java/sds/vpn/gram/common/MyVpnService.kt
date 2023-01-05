package sds.vpn.gram.common

import android.app.Activity
import android.content.Context
import androidx.core.app.ActivityCompat
import com.wireguard.android.backend.GoBackend
import com.wireguard.android.backend.Tunnel
import com.wireguard.config.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sds.vpn.gram.domain.model.Server

class MyVpnService(private val context: Context) {
    private val tunnel = WgTunnel()
    private val backend = GoBackend(context)

    fun isVpnConnected(): Boolean {
        return backend.getState(tunnel) == Tunnel.State.UP
    }

    fun disconnectVpn() {
        CoroutineScope(Dispatchers.IO).launch {
            println("VPN STATE : ${isVpnConnected()}")
            backend.setState(tunnel, Tunnel.State.DOWN, null)
            println("VPN STATE : ${isVpnConnected()}")
        }
    }

    fun connectWireguardTunnel(server: Server, activity: Activity) {
        val serverConfig = server.config!!
        val prepareIntent = GoBackend.VpnService.prepare(context)
        if(prepareIntent != null) {
            ActivityCompat.startActivityForResult(activity, prepareIntent, 0, null)
        }

        val interfaceBuilder = Interface.Builder()
        val peerBuilder = Peer.Builder()

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
            println("VPN STATE : ${isVpnConnected()}")
        }

    }

    class WgTunnel: Tunnel {
        override fun getName(): String {
            return "Vpngram"
        }

        override fun onStateChange(newState: Tunnel.State) {}
    }
}
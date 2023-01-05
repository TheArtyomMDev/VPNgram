package sds.vpn.gram.common

import android.app.Activity
import android.content.Context
import androidx.core.app.ActivityCompat
import com.wireguard.android.backend.Backend
import com.wireguard.android.backend.GoBackend
import com.wireguard.android.backend.Tunnel
import com.wireguard.config.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object VpnUtils {

    fun connectWireguardTunnel(context: Context) {
        /*
        val tunnel = WgTunnel()
        val prepareIntent = GoBackend.VpnService.prepare(context)
        if(prepareIntent != null) {
            ActivityCompat.startActivityForResult(context as Activity, prepareIntent, 0, null)
        }

        val interfaceBuilder = Interface.Builder()
        val peerBuilder = Peer.Builder()
        val backend: Backend = GoBackend(context)

        CoroutineScope(Dispatchers.IO).launch {
            backend.setState(
                tunnel, Tunnel.State.UP, Config.Builder()
                    .setInterface(
                        interfaceBuilder.addAddress(InetNetwork.parse("10.0.3.200/32"))
                            .parsePrivateKey("redacted").build()
                    )
                    .addPeer(
                        peerBuilder.addAllowedIp(InetNetwork.parse("0.0.0.0/0"))
                            .setEndpoint(InetEndpoint.parse("46.101.188.154:51820"))
                            .parsePublicKey("redacted").build()
                    )
                    .build()
            )
        }

         */
    }

    class WgTunnel: Tunnel {
        override fun getName(): String {
            return "Vpngram"
        }

        override fun onStateChange(newState: Tunnel.State) {}
    }
}
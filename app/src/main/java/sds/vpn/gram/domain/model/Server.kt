package sds.vpn.gram.domain.model

import sds.vpn.gram.data.remote.dto.GetVpnConfigResponse


data class Server(
    val serverId: String,
    val country: String,
    val IP: String,
    val port: Int = 80,
    val imageUrl: String,
    var ping: Long = 0,
    var config: GetVpnConfigResponse? = null
): java.io.Serializable

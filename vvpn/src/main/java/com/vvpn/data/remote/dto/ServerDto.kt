package com.vvpn.data.remote.dto

import com.vvpn.domain.model.Server
import java.net.URL

data class ServerDto(
    val id: String,
    val country: String,
    val ip: String,
    val imageUrl: String,
    val login: String,
    val password: String
)

fun ServerDto.toServer(): Server {
    val url = URL(this.ip)

    return Server(
        serverId = this.id,
        country = this.country,
        IP = url.host,
        port = url.port,
        imageUrl = this.imageUrl
    )
}

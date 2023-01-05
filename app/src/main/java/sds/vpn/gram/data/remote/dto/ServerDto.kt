package sds.vpn.gram.data.remote.dto

import sds.vpn.gram.domain.model.Server

data class ServerDto(
    val id: String,
    val country: String,
    val ip: String,
    val imageUrl: String,
    val login: String,
    val password: String
)

fun ServerDto.toServer(): Server {
    return Server(
        serverId = this.id,
        country = this.country,
        IP = this.ip,
        imageUrl = this.imageUrl
    )
}

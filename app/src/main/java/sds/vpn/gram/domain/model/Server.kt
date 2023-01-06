package sds.vpn.gram.domain.model


data class Server(
    val serverId: String,
    val country: String,
    val IP: String,
    val port: Int = 80,
    val imageUrl: String,
    var ping: Long = 0,
): java.io.Serializable

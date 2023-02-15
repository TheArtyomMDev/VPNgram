package sds.vpn.gram.domain.model

import java.io.Serializable

data class Server(
    val serverId: String,
    val country: String,
    val IP: String,
    val port: Int = 80,
    val imageUrl: String,
    var ping: Long = 0,
): Serializable {
    override fun equals(other: Any?): Boolean {
        return if (other is Server) {
            other.serverId == serverId
                    && other.country == country
                    && other.IP == IP
                    && other.port == port
                    && other.imageUrl == imageUrl
        } else false
    }

}

package com.vvpn.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VpnConfigDto(
    @SerializedName("InterfacePublicKey")
    val interfacePublicKey: String,

    @SerializedName("InterfacePrivateKey")
    val interfacePrivateKey: String,

    @SerializedName("addr")
    val address: String,

    @SerializedName("PeerPublicKey")
    val peerPublicKey: String,

    @SerializedName("serverPort")
    val serverPort: Int,
)

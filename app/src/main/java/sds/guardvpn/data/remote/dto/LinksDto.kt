package sds.guardvpn.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LinksDto(
    @SerializedName("payment_link")
    val paymentLink: String,

    @SerializedName("invite_link")
    val inviteLink: String,

    @SerializedName("invite_text")
    val inviteText: String
    )

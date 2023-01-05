package sds.vpn.gram.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GetTrafficLimitResponse(
    @SerializedName("traffic_limit")
    val trafficLimit: Double,

    @SerializedName("spended_traffic")
    val trafficSpent: Double
)

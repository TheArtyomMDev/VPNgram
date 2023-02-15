package sds.vpn.gram.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TrafficLimitDto(
    @SerializedName("traffic_limit")
    val trafficLimit: String,

    @SerializedName("spended_traffic")
    val trafficSpent: Double
)

package com.vvpn.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.vvpn.domain.model.TrafficLimit
import com.vvpn.domain.model.TrafficType

data class TrafficLimitDto(
    @SerializedName("traffic_limit")
    val trafficLimit: String,

    @SerializedName("spended_traffic")
    val trafficSpent: Double
)

fun TrafficLimitDto.toTrafficLimit(): TrafficLimit {
    return if (this.trafficLimit.contains("Unlimited"))
        TrafficLimit(
            this.trafficSpent,
            TrafficType.Unlimited
        )

    else TrafficLimit(
        this.trafficSpent,
        TrafficType.Free(this.trafficLimit.toDouble())
    )
}
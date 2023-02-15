package sds.vpn.gram.domain.model

import com.google.gson.annotations.SerializedName
import sds.vpn.gram.data.remote.dto.TrafficLimitDto

data class TrafficLimit(
    val trafficSpent: Double,
    val trafficType: TrafficType
)

sealed class TrafficType {
    data class Free(val trafficLimit: Double): TrafficType()
    object Unlimited: TrafficType()
}

fun TrafficLimitDto.toTrafficLimit(): TrafficLimit {
    return if (this.trafficLimit.contains("unlimited"))
        TrafficLimit(
            this.trafficSpent,
            TrafficType.Unlimited
        )

    else TrafficLimit(
        this.trafficSpent,
        TrafficType.Free(this.trafficLimit.toDouble())
    )
}
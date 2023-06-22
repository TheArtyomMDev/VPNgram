package sds.guardvpn.domain.model

import sds.guardvpn.data.remote.dto.TrafficLimitDto
import sds.guardvpn.data.remote.dto.toTrafficLimit

data class TrafficLimit(
    val trafficSpent: Double,
    val trafficType: TrafficType
)

// DELETE THIS ASAP
fun TrafficLimit.toTrafficDto(): TrafficLimitDto {

    return when(this.trafficType) {
        is TrafficType.Unlimited ->
            TrafficLimitDto(
                this.trafficSpent.toString(),
                Double.MAX_VALUE
            )
        is TrafficType.Free ->
            TrafficLimitDto(
                this.trafficSpent.toString(),
                this.trafficType.trafficLimit
            )
    }
}


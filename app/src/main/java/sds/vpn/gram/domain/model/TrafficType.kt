package sds.vpn.gram.domain.model

sealed class TrafficType {
    data class Free(val trafficLimit: Double): TrafficType()
    object Unlimited: TrafficType()
}
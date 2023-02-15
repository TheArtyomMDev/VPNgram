package sds.vpn.gram.ui.premium

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sds.vpn.gram.common.DeviceUtils
import sds.vpn.gram.common.ResourceProvider
import sds.vpn.gram.domain.model.TrafficLimit
import sds.vpn.gram.domain.model.TrafficType
import sds.vpn.gram.domain.repository.UserRepository


class PremiumViewModel(
    private val userRepository: UserRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _trafficLimitConfig = MutableStateFlow(TrafficLimit(0.0, TrafficType.Free(0.0)))
    val trafficLimitConfig = _trafficLimitConfig.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            _trafficLimitConfig.emit(
                userRepository.getTrafficLimit(
                    DeviceUtils.getAndroidID(resourceProvider.context)
                )
            )
        }
    }
}
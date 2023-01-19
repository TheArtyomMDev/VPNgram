package sds.vpn.gram.ui.permissions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sds.vpn.gram.common.DeviceUtils
import sds.vpn.gram.common.ResourceProvider
import sds.vpn.gram.data.remote.dto.GetTrafficLimitResponse
import sds.vpn.gram.domain.model.Permission
import sds.vpn.gram.domain.model.Server
import sds.vpn.gram.domain.repository.PermissionsRepository
import sds.vpn.gram.domain.repository.ServerRepository
import sds.vpn.gram.domain.repository.UserRepository


class PermissionsViewModel(
    private val permissionsRepository: PermissionsRepository,
) : ViewModel() {
    private val _permissionsList = MutableStateFlow<List<Permission>>(emptyList())
    val permissionsList = _permissionsList.asStateFlow()

    private val _isAllGranted = MutableStateFlow(false)
    val isAllGranted = _isAllGranted.asStateFlow()

    init {
        viewModelScope.launch {
            _permissionsList.emit(permissionsRepository.getAllPermissions())
            _isAllGranted.emit(permissionsRepository.checkAllPermissionsGranted())
        }
    }

    fun updateIsAllGranted() {
        viewModelScope.launch {
            _isAllGranted.emit(permissionsRepository.checkAllPermissionsGranted())
        }
    }
}
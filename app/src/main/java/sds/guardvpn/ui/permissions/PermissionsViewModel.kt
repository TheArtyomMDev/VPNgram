package sds.guardvpn.ui.permissions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sds.guardvpn.domain.model.Permission
import sds.guardvpn.domain.repository.PermissionsRepository


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
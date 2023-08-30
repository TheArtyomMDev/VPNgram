package com.vvpn.domain.repository

import com.vvpn.domain.model.Permission

interface PermissionsRepository {

    fun checkAllPermissionsGranted(): Boolean

    fun getAllPermissions(): List<Permission>
}
package sds.vpn.gram.domain.repository

import sds.vpn.gram.domain.model.Permission

interface PermissionsRepository {

    fun checkAllPermissionsGranted(): Boolean

    fun getAllPermissions(): List<Permission>
}
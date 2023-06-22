package sds.guardvpn.domain.repository

import sds.guardvpn.domain.model.Permission

interface PermissionsRepository {

    fun checkAllPermissionsGranted(): Boolean

    fun getAllPermissions(): List<Permission>
}
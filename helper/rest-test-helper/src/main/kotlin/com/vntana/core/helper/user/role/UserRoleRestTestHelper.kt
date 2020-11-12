package com.vntana.core.helper.user.role

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.role.request.*

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 11:58 AM
 */
open class UserRoleRestTestHelper : AbstractRestTestHelper() {

    fun buildUserRoleGrantSuperAdminRequest(userUuid: String? = uuid()): UserRoleGrantSuperAdminRequest = UserRoleGrantSuperAdminRequest(userUuid)

    fun buildUserRoleRevokeOrganizationAdminRequest(
            organizationUuid: String? = uuid(),
            userUuid: String? = uuid()
    ): UserRoleRevokeOrganizationAdminRequest = UserRoleRevokeOrganizationAdminRequest(userUuid, organizationUuid)

    fun buildUserRoleGrantOrganizationAdminRequest(
            organizationUuid: String? = uuid(),
            userUuid: String? = uuid()
    ): UserRoleGrantOrganizationAdminRequest = UserRoleGrantOrganizationAdminRequest(userUuid, organizationUuid)

    fun buildUserRoleGrantClientRequest(
            userUuid: String? = uuid(),
            clientUuid: String? = uuid(),
            userRole: UserRoleModel? = UserRoleModel.CLIENT_ORGANIZATION_ADMIN
    ): UserRoleGrantClientOrganizationRequest = UserRoleGrantClientOrganizationRequest(userUuid, clientUuid, userRole)

    fun buildUserRoleRevokeClientRequest(
            userUuid: String? = uuid(),
            clientUuid: String? = uuid(),
            userRole: UserRoleModel? = UserRoleModel.CLIENT_ORGANIZATION_ADMIN
    ): UserRoleRevokeClientRequest = UserRoleRevokeClientRequest(userUuid, clientUuid, userRole)
}
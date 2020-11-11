package com.vntana.core.helper.user.role

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.model.user.role.request.UserRoleGrantClientAdminOrganizationRequest
import com.vntana.core.model.user.role.request.UserRoleGrantOrganizationAdminRequest
import com.vntana.core.model.user.role.request.UserRoleGrantSuperAdminRequest
import com.vntana.core.model.user.role.request.UserRoleRevokeOrganizationAdminRequest

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
    ): UserRoleRevokeOrganizationAdminRequest = UserRoleRevokeOrganizationAdminRequest(organizationUuid, userUuid)

    fun buildUserRoleGrantOrganizationAdminRequest(
            organizationUuid: String? = uuid(),
            userUuid: String? = uuid()
    ): UserRoleGrantOrganizationAdminRequest = UserRoleGrantOrganizationAdminRequest(userUuid, organizationUuid)

    fun buildUserRoleGrantClientAdminRequest(
            userUuid: String? = uuid(),
            clientOrganizationUuid: String? = uuid()
    ): UserRoleGrantClientAdminOrganizationRequest = UserRoleGrantClientAdminOrganizationRequest(userUuid, clientOrganizationUuid)
}
package com.vntana.core.helper.user.role

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.model.user.role.request.UserRoleGrantOrganizationAdminRequest
import com.vntana.core.model.user.role.request.UserRoleRevokeOrganizationAdminRequest

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 11:58 AM
 */
open class UserRoleRestTestHelper : AbstractRestTestHelper() {

    fun buildUserRoleRevokeOrganizationAdminRequest(
            organizationUuid: String? = uuid(),
            userUuid: String? = uuid()
    ): UserRoleRevokeOrganizationAdminRequest = UserRoleRevokeOrganizationAdminRequest(organizationUuid, userUuid)

    fun buildUserRoleGrantOrganizationAdminRequest(
            organizationUuid: String? = uuid(),
            userUuid: String? = uuid()
    ): UserRoleGrantOrganizationAdminRequest = UserRoleGrantOrganizationAdminRequest(organizationUuid, userUuid)
}
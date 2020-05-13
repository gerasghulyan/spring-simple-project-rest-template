package com.vntana.core.helper.unit.user.role

import com.vntana.core.domain.client.ClientOrganization
import com.vntana.core.domain.organization.Organization
import com.vntana.core.domain.user.*
import com.vntana.core.helper.AbstractTestHelper
import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.service.user.role.dto.UserGrantOrganizationRoleDto
import com.vntana.core.service.user.role.dto.UserRevokeOrganizationAdminRoleDto

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 4:15 PM
 */
open class UserRoleCommonTestHelper : AbstractTestHelper() {

    private val userCommonTestHelper = UserCommonTestHelper()
    private val organizationCommonTestHelper = OrganizationCommonTestHelper()
    private val clientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()

    fun buildUserOrganizationOwnerRole(user: User? = userCommonTestHelper.buildUserWithOrganizationOwnerRole(),
                                       organization: Organization? = organizationCommonTestHelper.buildOrganization()
    ): UserOrganizationOwnerRole = UserOrganizationOwnerRole(user, organization)

    fun buildUserOrganizationAdminRole(user: User? = userCommonTestHelper.buildUserWithOrganizationOwnerRole(),
                                       organization: Organization? = organizationCommonTestHelper.buildOrganization()
    ): UserOrganizationAdminRole = UserOrganizationAdminRole(user, organization)

    fun buildUserClientOrganizationRole(user: User? = userCommonTestHelper.buildUserWithOrganizationOwnerRole(),
                                        clientOrganization: ClientOrganization? = clientOrganizationCommonTestHelper.buildClientOrganization()
    ): UserClientOrganizationRole = UserClientOrganizationRole(user, UserRole.CLIENT_ADMIN, clientOrganization)

    fun buildUserGrantOrganizationRoleDto(
            userUuid: String? = uuid(),
            organizationUuid: String? = uuid()
    ): UserGrantOrganizationRoleDto = UserGrantOrganizationRoleDto(userUuid, organizationUuid)

    fun buildUserRevokeOrganizationAdminRoleDto(
            userUuid: String? = uuid(),
            organizationUuid: String? = uuid()
    ): UserRevokeOrganizationAdminRoleDto = UserRevokeOrganizationAdminRoleDto(userUuid, organizationUuid)

}
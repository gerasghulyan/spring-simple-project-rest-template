package com.vntana.core.helper.integration.user.role

import com.vntana.core.domain.organization.Organization
import com.vntana.core.domain.user.User
import com.vntana.core.domain.user.UserOrganizationAdminRole
import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.helper.unit.user.role.UserRoleCommonTestHelper
import com.vntana.core.service.user.role.UserRoleService
import com.vntana.core.service.user.role.dto.UserGrantOrganizationRoleDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arman Gevorgyan.
 * Date: 5/8/20
 * Time: 5:17 PM
 */
@Component
class UserRoleIntegrationTestHelper : UserRoleCommonTestHelper() {

    @Autowired
    private lateinit var userRoleService: UserRoleService

    @Autowired
    private lateinit var userIntegrationTestHelper: UserIntegrationTestHelper

    @Autowired
    private lateinit var organizationIntegrationTestHelper: OrganizationIntegrationTestHelper

    fun persistUserOrganizationAdminRole(user: User = userIntegrationTestHelper.persistUserWithOwnerRole(),
                                         organization: Organization = organizationIntegrationTestHelper.persistOrganization()
    ): UserOrganizationAdminRole = userRoleService.grantOrganizationAdminRole(UserGrantOrganizationRoleDto(user.uuid, organization.uuid))
}
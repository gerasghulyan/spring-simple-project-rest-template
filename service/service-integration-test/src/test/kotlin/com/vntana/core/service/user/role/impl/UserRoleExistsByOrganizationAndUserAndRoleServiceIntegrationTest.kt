package com.vntana.core.service.user.role.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.user.role.AbstractUserRoleServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 12:44 PM
 */
class UserRoleExistsByOrganizationAndUserAndRoleServiceIntegrationTest : AbstractUserRoleServiceIntegrationTest() {

    @Test
    fun `test when noting found`() {
        val result = userRoleService.existsByOrganizationAndUserAndRole(uuid(), uuid(), UserRole.ORGANIZATION_OWNER)
        assertThat(result).isFalse()
    }

    @Test
    fun `test admin role`() {
        val role = integrationTestHelper.persistUserOrganizationAdminRole()
        val organizationUuid = role.organization.uuid
        flushAndClear()
        val result = userRoleService.existsByOrganizationAndUserAndRole(organizationUuid,
                role.user.uuid,
                UserRole.ORGANIZATION_ADMIN
        )
        assertThat(result).isTrue()
    }
}
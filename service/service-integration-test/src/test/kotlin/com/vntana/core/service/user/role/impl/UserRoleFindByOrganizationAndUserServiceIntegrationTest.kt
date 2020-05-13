package com.vntana.core.service.user.role.impl

import com.vntana.core.service.user.role.AbstractUserRoleServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/13/20
 * Time: 10:01 AM
 */
class UserRoleFindByOrganizationAndUserServiceIntegrationTest : AbstractUserRoleServiceIntegrationTest() {

    @Test
    fun `test when not found`() {
        assertThat(userRoleService.findByOrganizationAndUser(uuid(), uuid())).isEmpty
    }

    @Test
    fun `test admin role`() {
        val adminRole = integrationTestHelper.persistUserOrganizationAdminRole()
        val organizationUuid = adminRole.organization.uuid
        val userUuid = adminRole.user.uuid
        flushAndClear()
        userRoleService.findByOrganizationAndUser(organizationUuid, userUuid).let {
            flushAndClear()
            assertThat(it).isPresent
            assertThat(it.get()).isEqualTo(adminRole)
        }
    }

    @Test
    fun `test owner role`() {
        val organization = organizationIntegrationTestHelper.persistOrganization()
        val user = userIntegrationTestHelper.persistUser(organizationUuid = organization.uuid)
        val organizationOwnerRole = user.roleOfOrganizationOwner(organization).get()
        flushAndClear()
        userRoleService.findByOrganizationAndUser(organization.uuid, user.uuid).let {
            flushAndClear()
            assertThat(it).isPresent
            assertThat(it.get()).isEqualTo(organizationOwnerRole)
        }
    }
}
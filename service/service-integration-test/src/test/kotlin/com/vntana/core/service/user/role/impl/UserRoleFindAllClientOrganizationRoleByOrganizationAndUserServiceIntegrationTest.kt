package com.vntana.core.service.user.role.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.user.role.AbstractUserRoleServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 06.11.2020
 * Time: 14:13
 */
class UserRoleFindAllClientOrganizationRoleByOrganizationAndUserServiceIntegrationTest : AbstractUserRoleServiceIntegrationTest() {

    @Test
    fun `test when noting found`() {
        val result = userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(uuid(), uuid())
        assertThat(result).isEmpty()
    }

    @Test
    fun `test multiple client roles in organization`() {
        val persistOrganization = organizationIntegrationTestHelper.persistOrganization()
        val clientOrganization1 = clientOrganizationIntegrationTestHelper.persistClientOrganization(organizationUuid = persistOrganization.uuid)
        val clientOrganization2 = clientOrganizationIntegrationTestHelper.persistClientOrganization(organizationUuid = persistOrganization.uuid)
        val persistUser = userIntegrationTestHelper.persistUser()
        integrationTestHelper.persistUserClientRole(
                user = persistUser,
                clientOrganization = clientOrganization1,
                clientRole = UserRole.CLIENT_ORGANIZATION_ADMIN
        )
        integrationTestHelper.persistUserClientRole(
                user = persistUser,
                clientOrganization = clientOrganization2,
                clientRole = UserRole.CLIENT_ORGANIZATION_VIEWER
        )
        flushAndClear()
        val result = userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(
                persistOrganization.uuid,
                persistUser.uuid
        )
        assertThat(result).isNotEmpty()
    }

    @Test
    fun `test client admin`() {
        val clientOrganization = clientOrganizationIntegrationTestHelper.persistClientOrganization()
        val clientAdminRole = integrationTestHelper.persistUserClientRole(
                clientOrganization = clientOrganization,
                clientRole = UserRole.CLIENT_ORGANIZATION_ADMIN
        )
        flushAndClear()
        val result = userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(
                clientOrganization.organization.uuid,
                clientAdminRole.user.uuid
        )
        assertThat(result).isNotEmpty()
    }

    @Test
    fun `test client content manager`() {
        val clientOrganization = clientOrganizationIntegrationTestHelper.persistClientOrganization()
        val clientContentManagerRole = integrationTestHelper.persistUserClientRole(
                clientOrganization = clientOrganization,
                clientRole = UserRole.CLIENT_ORGANIZATION_CONTENT_MANAGER
        )
        flushAndClear()
        val result = userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(
                clientOrganization.organization.uuid,
                clientContentManagerRole.user.uuid
        )
        assertThat(result).isNotEmpty()
    }

    @Test
    fun `test client viewer`() {
        val clientOrganization = clientOrganizationIntegrationTestHelper.persistClientOrganization()
        val clientViewerRole = integrationTestHelper.persistUserClientRole(
                clientOrganization = clientOrganization,
                clientRole = UserRole.CLIENT_ORGANIZATION_VIEWER
        )
        flushAndClear()
        val result = userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(
                clientOrganization.organization.uuid,
                clientViewerRole.user.uuid
        )
        assertThat(result).isNotEmpty()
    }
}
package com.vntana.core.service.user.role.impl

import com.vntana.core.service.user.role.AbstractUserRoleServiceIntegrationTest
import com.vntana.core.service.user.role.dto.UserClientRole
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 06.11.2020
 * Time: 14:13
 */
class UserRoleExistsByClientOrganizationAndUserServiceIntegrationTest : AbstractUserRoleServiceIntegrationTest() {

    @Test
    fun `test when noting found`() {
        val result = userRoleService.existsClientOrganizationRoleByOrganizationAndUser(uuid(), uuid())
        assertThat(result).isFalse()
    }

    @Test
    fun `test client admin`() {
        val clientOrganization = clientOrganizationIntegrationTestHelper.persistClientOrganization()
        val clientAdminRole = integrationTestHelper.persistUserClientRole(
                clientOrganization = clientOrganization,
                clientRole = UserClientRole.CLIENT_ADMIN
        )
        flushAndClear()
        val result = userRoleService.existsClientOrganizationRoleByOrganizationAndUser(
                clientOrganization.organization.uuid,
                clientAdminRole.user.uuid
        )
        assertThat(result).isTrue()
    }
    
    @Test
    fun `test client content manager`() {
        val clientOrganization = clientOrganizationIntegrationTestHelper.persistClientOrganization()
        val clientAdminRole = integrationTestHelper.persistUserClientRole(
                clientOrganization = clientOrganization,
                clientRole = UserClientRole.CLIENT_CONTENT_MANAGER
        )
        flushAndClear()
        val result = userRoleService.existsClientOrganizationRoleByOrganizationAndUser(
                clientOrganization.organization.uuid,
                clientAdminRole.user.uuid
        )
        assertThat(result).isTrue()
    }
    
    @Test
    fun `test client viewer`() {
        val clientOrganization = clientOrganizationIntegrationTestHelper.persistClientOrganization()
        val clientAdminRole = integrationTestHelper.persistUserClientRole(
                clientOrganization = clientOrganization,
                clientRole = UserClientRole.CLIENT_VIEWER
        )
        flushAndClear()
        val result = userRoleService.existsClientOrganizationRoleByOrganizationAndUser(
                clientOrganization.organization.uuid,
                clientAdminRole.user.uuid
        )
        assertThat(result).isTrue()
    }
}
package com.vntana.core.service.user.role.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.user.role.AbstractUserRoleServiceIntegrationTest
import com.vntana.core.service.user.role.exception.UserClientRoleNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 05.11.2020
 * Time: 12:13
 */
class UserRoleRevokeClientRoleServiceIntegrationTest : AbstractUserRoleServiceIntegrationTest() {

    @Test
    fun `test when not found`() {
        val revokeDto = integrationTestHelper.buildUserRevokeClientRoleDto()
        assertThatThrownBy { userRoleService.revokeClientRole(revokeDto) }
                .isExactlyInstanceOf(UserClientRoleNotFoundException::class.java)
    }

    @Test
    fun `test client admin`() {
        val clientOrganization = clientOrganizationIntegrationTestHelper.persistClientOrganization()
        val clientAdminRole = integrationTestHelper.persistUserClientRole(
                clientOrganization = clientOrganization, 
                clientRole = UserRole.CLIENT_ORGANIZATION_ADMIN
        )
        val user = clientAdminRole.user
        val revokeDto = integrationTestHelper.buildUserRevokeClientRoleDto(
                userUuid = user.uuid,
                clientOrganizationUuid = clientOrganization.uuid,
                clientRole = UserRole.CLIENT_ORGANIZATION_ADMIN
        )
        flushAndClear()
        userRoleService.revokeClientRole(revokeDto).let {
            flushAndClear()
            val roles = userService.getByUuid(user.uuid).roles()
            assertThat(roles).doesNotContain(clientAdminRole)
        }
    }
    
    @Test
    fun `test client content manager`() {
        val clientOrganization = clientOrganizationIntegrationTestHelper.persistClientOrganization()
        val clientAdminRole = integrationTestHelper.persistUserClientRole(
                clientOrganization = clientOrganization, 
                clientRole = UserRole.CLIENT_ORGANIZATION_CONTENT_MANAGER
        )
        val user = clientAdminRole.user
        val revokeDto = integrationTestHelper.buildUserRevokeClientRoleDto(
                userUuid = user.uuid,
                clientOrganizationUuid = clientOrganization.uuid,
                clientRole = UserRole.CLIENT_ORGANIZATION_CONTENT_MANAGER
        )
        flushAndClear()
        userRoleService.revokeClientRole(revokeDto).let {
            flushAndClear()
            val roles = userService.getByUuid(user.uuid).roles()
            assertThat(roles).doesNotContain(clientAdminRole)
        }
    }
    
    @Test
    fun `test client viewer`() {
        val clientOrganization = clientOrganizationIntegrationTestHelper.persistClientOrganization()
        val clientAdminRole = integrationTestHelper.persistUserClientRole(
                clientOrganization = clientOrganization, 
                clientRole = UserRole.CLIENT_ORGANIZATION_VIEWER
        )
        val user = clientAdminRole.user
        val revokeDto = integrationTestHelper.buildUserRevokeClientRoleDto(
                userUuid = user.uuid,
                clientOrganizationUuid = clientOrganization.uuid,
                clientRole = UserRole.CLIENT_ORGANIZATION_VIEWER
        )
        flushAndClear()
        userRoleService.revokeClientRole(revokeDto).let {
            flushAndClear()
            val roles = userService.getByUuid(user.uuid).roles()
            assertThat(roles).doesNotContain(clientAdminRole)
        }
    }
}
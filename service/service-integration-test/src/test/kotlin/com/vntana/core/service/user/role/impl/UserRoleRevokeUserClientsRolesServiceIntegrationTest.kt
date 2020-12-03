package com.vntana.core.service.user.role.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.user.role.AbstractUserRoleServiceIntegrationTest
import com.vntana.core.service.user.role.exception.UserClientsIncorrectRolesRevokeException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Manuk Gharslyan
 * Date: 05.11.2020
 * Time: 12:13
 */
class UserRoleRevokeUserClientsRolesServiceIntegrationTest : AbstractUserRoleServiceIntegrationTest() {

    @Test
    fun `test when not found`() {
        val revokeDto = integrationTestHelper.buildUserRevokeClientsRolesDto()
        assertThatThrownBy { userRoleService.revokeUserClientsRoles(revokeDto) }
                .isExactlyInstanceOf(UserClientsIncorrectRolesRevokeException::class.java)
    }

    @Test
    fun test() {
        val user = userIntegrationTestHelper.persistUser()
        val clientOrganization1 = clientOrganizationIntegrationTestHelper.persistClientOrganization()
        val clientOrganization2 = clientOrganizationIntegrationTestHelper.persistClientOrganization()
        val clientOrganization3 = clientOrganizationIntegrationTestHelper.persistClientOrganization()
        integrationTestHelper.persistUserClientRole(user = user, clientOrganization = clientOrganization1, clientRole = UserRole.CLIENT_ORGANIZATION_ADMIN)
        integrationTestHelper.persistUserClientRole(user = user, clientOrganization = clientOrganization2, clientRole = UserRole.CLIENT_ORGANIZATION_CONTENT_MANAGER)
        integrationTestHelper.persistUserClientRole(user = user, clientOrganization = clientOrganization3, clientRole = UserRole.CLIENT_ORGANIZATION_VIEWER)
        val revokeClientsRolesDto = integrationTestHelper.buildUserRevokeClientsRolesDto(userUuid = user.uuid, clientUuids = listOf(clientOrganization1.uuid, clientOrganization2.uuid, clientOrganization3.uuid))
        flushAndClear()
        userRoleService.revokeUserClientsRoles(revokeClientsRolesDto)
    }
}
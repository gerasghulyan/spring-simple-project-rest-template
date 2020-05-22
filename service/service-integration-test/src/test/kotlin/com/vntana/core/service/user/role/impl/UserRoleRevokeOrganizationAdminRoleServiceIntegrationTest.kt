package com.vntana.core.service.user.role.impl

import com.vntana.core.domain.user.UserOrganizationOwnerRole
import com.vntana.core.service.user.role.AbstractUserRoleServiceIntegrationTest
import com.vntana.core.service.user.role.exception.UserOrganizationAdminRoleNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/8/20
 * Time: 5:55 PM
 */
class UserRoleRevokeOrganizationAdminRoleServiceIntegrationTest : AbstractUserRoleServiceIntegrationTest() {

    @Test
    fun `test when not found`() {
        val revokeDto = integrationTestHelper.buildUserRevokeOrganizationAdminRoleDto()
        assertThatThrownBy { userRoleService.revokeOrganizationAdminRole(revokeDto) }
                .isExactlyInstanceOf(UserOrganizationAdminRoleNotFoundException::class.java)
    }

    @Test
    fun `test revoke admin when user has owner role in same organization`() {
        val organization = organizationIntegrationTestHelper.persistOrganization()
        val user = userIntegrationTestHelper.persistUserWithOwnerRole(organizationUuid = organization.uuid)
        val adminRole = integrationTestHelper.persistUserOrganizationAdminRole(user = user, organization = organization)
        val revokeDto = integrationTestHelper.buildUserRevokeOrganizationAdminRoleDto(
                userUuid = user.uuid,
                organizationUuid = organization.uuid
        )
        flushAndClear()
        userRoleService.revokeOrganizationAdminRole(revokeDto).let {
            flushAndClear()
            val roles = userService.getByUuid(user.uuid).roles()
            assertThat(roles.size).isEqualTo(1)
            assertThat(roles[0]).isInstanceOf(UserOrganizationOwnerRole::class.java)
            assertThat(roles).doesNotContain(adminRole)
        }
    }

    @Test
    fun test() {
        val adminRole = integrationTestHelper.persistUserOrganizationAdminRole()
        val user = adminRole.user
        val organization = adminRole.organization
        val revokeDto = integrationTestHelper.buildUserRevokeOrganizationAdminRoleDto(
                userUuid = user.uuid,
                organizationUuid = organization.uuid
        )
        flushAndClear()
        userRoleService.revokeOrganizationAdminRole(revokeDto).let {
            flushAndClear()
            val roles = userService.getByUuid(user.uuid).roles()
            assertThat(roles).doesNotContain(adminRole)
        }
    }
}
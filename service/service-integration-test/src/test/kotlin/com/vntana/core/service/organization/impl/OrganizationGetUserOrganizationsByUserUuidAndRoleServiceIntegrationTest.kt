package com.vntana.core.service.organization.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.organization.AbstractOrganizationServiceIntegrationTest
import com.vntana.core.service.user.role.dto.UserGrantOrganizationRoleDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 3/6/2020
 * Time: 6:14 PM
 */
class OrganizationGetUserOrganizationsByUserUuidAndRoleServiceIntegrationTest : AbstractOrganizationServiceIntegrationTest() {

    @Test
    fun `get organizations by user uuid and role ORGANIZATION_OWNER`() {
        val organization1 = integrationTestHelper.persistOrganization()
        val user = userIntegrationTestHelper.persistUser(organizationUuid = organization1.uuid)
        val organization2 = integrationTestHelper.persistOrganization()
        val organization3 = integrationTestHelper.persistOrganization()
        userIntegrationTestHelper.grantOrganizationOwnerRole(uuid = user.uuid, organizationUuid = organization3.uuid)
        userRoleService.grantOrganizationAdminRole(UserGrantOrganizationRoleDto(user.uuid, organization2.uuid))
        flushAndClear()
        organizationService.getUserOrganizationsByUserUuidAndRole(integrationTestHelper.buildGetUserOrganizationsByUserUuidAndRoleDto(
                userUuid = user.uuid,
                userRole = UserRole.ORGANIZATION_OWNER
        )).let {
            assertThat(it).isNotEmpty
            assertThat(it.size).isEqualTo(2)
            assertThat(it).containsExactlyElementsOf(mutableListOf(organization1, organization3))
        }
    }

    @Test
    fun `get organizations by user uuid and role ORGANIZATION_ADMIN`() {
        val organization1 = integrationTestHelper.persistOrganization()
        val user = userIntegrationTestHelper.persistUser(organizationUuid = organization1.uuid)
        val organization2 = integrationTestHelper.persistOrganization()
        val organization3 = integrationTestHelper.persistOrganization()
        userIntegrationTestHelper.grantOrganizationOwnerRole(uuid = user.uuid, organizationUuid = organization3.uuid)
        userRoleService.grantOrganizationAdminRole(UserGrantOrganizationRoleDto(user.uuid, organization2.uuid))
        flushAndClear()
        organizationService.getUserOrganizationsByUserUuidAndRole(integrationTestHelper.buildGetUserOrganizationsByUserUuidAndRoleDto(
                userUuid = user.uuid,
                userRole = UserRole.ORGANIZATION_ADMIN
        )).let {
            assertThat(it).isNotEmpty
            assertThat(it.size).isEqualTo(1)
            assertThat(it).containsExactlyElementsOf(mutableListOf(organization2))
        }
    }
}
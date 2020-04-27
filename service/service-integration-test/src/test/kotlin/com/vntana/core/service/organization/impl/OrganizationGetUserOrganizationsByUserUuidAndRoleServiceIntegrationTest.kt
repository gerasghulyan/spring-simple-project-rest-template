package com.vntana.core.service.organization.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.organization.AbstractOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 3/6/2020
 * Time: 6:14 PM
 */
class OrganizationGetUserOrganizationsByUserUuidAndRoleServiceIntegrationTest : AbstractOrganizationServiceIntegrationTest() {

    @Test
    fun `get organizations by user uuid and role`() {
        val organization1 = integrationTestHelper.persistOrganization()
        val user = userIntegrationTestHelper.persistUser(organizationUuid = organization1.uuid)
        val organization2 = integrationTestHelper.persistOrganization()
        val organization3 = integrationTestHelper.persistOrganization()
        userIntegrationTestHelper.grantOrganizationRole(uuid = user.uuid, organizationUuid = organization2.uuid)
        userIntegrationTestHelper.grantOrganizationRole(uuid = user.uuid, organizationUuid = organization3.uuid)
        flushAndClear()
        organizationService.getUserOrganizationsByUserUuidAndRole(integrationTestHelper.buildGetUserOrganizationsByUserUuidAndRoleDto(
                userUuid = user.uuid,
                userRole = UserRole.ORGANIZATION_ADMIN
        )).let {
            assertThat(it).isNotEmpty
            assertThat(it.size).isEqualTo(3)
            assertThat(it).containsExactlyElementsOf(mutableListOf(organization1, organization2, organization3))
        }
    }
}
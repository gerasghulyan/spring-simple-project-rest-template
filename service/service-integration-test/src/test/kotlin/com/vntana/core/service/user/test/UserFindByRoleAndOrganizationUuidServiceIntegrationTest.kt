package com.vntana.core.service.user.test

import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.user.AbstractUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 4/7/2020
 * Time: 12:09 PM
 */
class UserFindByRoleAndOrganizationUuidServiceIntegrationTest : AbstractUserServiceIntegrationTest() {

    @Test
    fun `test find`() {
        val organization = organizationIntegrationTest.persistOrganization()
        val user = integrationTestHelper.persistUserWithOwnerRole(organizationUuid = organization.uuid)
        userService.findByRoleAndOrganizationUuid(UserRole.ORGANIZATION_OWNER, organization.uuid).let {
            assertThat(it).isNotEmpty
            assertThat(it).containsOnly(user)
        }
    }

    @Test
    fun `test not found`() {
        val organization = organizationIntegrationTest.persistOrganization()
        integrationTestHelper.persistUserWithOwnerRole(organizationUuid = organization.uuid)
        userService.findByRoleAndOrganizationUuid(UserRole.ASSET_MANAGER, organization.uuid).let {
            assertThat(it).isEmpty()
        }
    }
}
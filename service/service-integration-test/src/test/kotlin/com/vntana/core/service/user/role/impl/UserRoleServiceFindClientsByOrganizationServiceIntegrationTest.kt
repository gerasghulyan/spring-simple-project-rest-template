package com.vntana.core.service.user.role.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.user.role.AbstractUserRoleServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 10.11.2020
 * Time: 13:29
 */
class UserRoleServiceFindClientsByOrganizationServiceIntegrationTest : AbstractUserRoleServiceIntegrationTest() {

    @Test
    fun `test when noting found`() {
        flushAndClear()
        userRoleService.findClientsByOrganization(uuid()).let {
            assertThat(it.size).isEqualTo(0)
        }
    }

    @Test
    fun `test client viewer and client admin`() {
        val organization = organizationIntegrationTestHelper.persistOrganization()
        val clientOrganization1 = clientOrganizationIntegrationTestHelper.persistClientOrganization(organizationUuid = organization.uuid)
        val clientOrganization2 = clientOrganizationIntegrationTestHelper.persistClientOrganization(organizationUuid = organization.uuid)
        val user1 = userIntegrationTestHelper.persistUser()
        user1.grantClientRole(clientOrganization1, UserRole.CLIENT_VIEWER)
        val user2 = userIntegrationTestHelper.persistUser()
        user2.grantClientRole(clientOrganization2, UserRole.CLIENT_ADMIN)
        flushAndClear()
        userRoleService.findClientsByOrganization(organization.uuid).let {
            assertThat(it.size).isEqualTo(2)
            assertThat(listOf(it[0].user, it[1].user)).containsExactlyInAnyOrder(user1, user2)
            assertThat(it.map { role -> role.userRole }.toList()).containsOnly(UserRole.CLIENT_ADMIN, UserRole.CLIENT_VIEWER)
        }
    }
}
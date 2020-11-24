package com.vntana.core.service.user.role.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.user.role.AbstractUserRoleServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/7/20
 * Time: 2:13 PM
 */
class UserRoleServiceFindAllByClientOrganizationServiceIntegrationTest : AbstractUserRoleServiceIntegrationTest() {

    @Test
    fun `test when noting found`() {
        val clientUuid = clientOrganizationIntegrationTestHelper.persistClientOrganization().uuid
        flushAndClear()
        userRoleService.findAllByClientOrganization(clientUuid).let {
            assertThat(it.size).isEqualTo(0)
        }
    }

    @Test
    fun test() {
        val user = userIntegrationTestHelper.persistUser()
        val user1 = userIntegrationTestHelper.persistUser()
        val user2 = userIntegrationTestHelper.persistUser()
        val clientUuid = clientOrganizationIntegrationTestHelper.persistClientOrganization().uuid
        userIntegrationTestHelper.grantClientRole(userUuid = user.uuid, clientOrganizationUuid = clientUuid)
        userIntegrationTestHelper.grantClientRole(userUuid = user1.uuid, clientOrganizationUuid = clientUuid, clientRole = UserRole.CLIENT_ORGANIZATION_CONTENT_MANAGER)
        userIntegrationTestHelper.grantClientRole(userUuid = user2.uuid, clientOrganizationUuid = clientUuid, clientRole = UserRole.CLIENT_ORGANIZATION_CONTENT_MANAGER)
        flushAndClear()
        userRoleService.findAllByClientOrganization(clientUuid).let {
            assertThat(it.size).isEqualTo(3)
        }
    }
}
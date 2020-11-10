package com.vntana.core.service.user.role.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.user.role.AbstractUserRoleServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 10.11.2020
 * Time: 13:52
 */
class UserRoleServiceFindByClientOrganizationAndUserServiceIntegrationTest : AbstractUserRoleServiceIntegrationTest() {
    
    @Test
    fun `test when not found`() {
        assertThat(userRoleService.findByClientOrganizationAndUser(uuid(), uuid())).isEmpty
    }

    @Test
    fun test() {
        val clientOrganization = clientOrganizationIntegrationTestHelper.persistClientOrganization()
        val clientRole = integrationTestHelper.persistUserClientRole(clientRole = UserRole.CLIENT_ORGANIZATION_CONTENT_MANAGER, clientOrganization = clientOrganization)
        flushAndClear()
        userRoleService.findByClientOrganizationAndUser(clientOrganization.uuid, clientRole.user.uuid).let {
            assertThat(it).isPresent
            assertThat(it.get()).isEqualTo(clientRole)
        }
    }
}
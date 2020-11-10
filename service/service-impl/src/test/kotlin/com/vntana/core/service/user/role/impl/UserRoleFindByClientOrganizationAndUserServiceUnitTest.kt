package com.vntana.core.service.user.role.impl

import com.vntana.core.service.user.role.AbstractUserRoleServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 09.11.2020
 * Time: 19:03
 */
class UserRoleFindByClientOrganizationAndUserServiceUnitTest : AbstractUserRoleServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertIllegalArgumentException { userRoleService.findByClientOrganizationAndUser(null, uuid()) }
        assertIllegalArgumentException { userRoleService.findByClientOrganizationAndUser(emptyString(), uuid()) }
        assertIllegalArgumentException { userRoleService.findByClientOrganizationAndUser(uuid(), null) }
        assertIllegalArgumentException { userRoleService.findByClientOrganizationAndUser(uuid(), emptyString()) }
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val role = commonTestHelper.buildUserClientAdminRole()
        expect(userRoleRepository.findByClientOrganizationAndUser(role.clientOrganization.uuid, role.user.uuid)).andReturn(Optional.empty())
        replayAll()
        assertThat(userRoleService.findByClientOrganizationAndUser(role.clientOrganization.uuid, role.user.uuid)).isEmpty
        verifyAll()
    }
    
    @Test
    fun test() {
        resetAll()
        val role = commonTestHelper.buildUserClientAdminRole()
        expect(userRoleRepository.findByClientOrganizationAndUser(role.clientOrganization.uuid, role.user.uuid)).andReturn(Optional.of(role))
        replayAll()
        userRoleService.findByClientOrganizationAndUser(role.clientOrganization.uuid, role.user.uuid).let {
            assertThat(it).isPresent
            assertThat(it.get()).isEqualTo(role)
        }
        verifyAll()
    }
}
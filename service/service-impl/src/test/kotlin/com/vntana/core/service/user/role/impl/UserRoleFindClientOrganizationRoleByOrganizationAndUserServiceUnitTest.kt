package com.vntana.core.service.user.role.impl

import com.vntana.core.service.user.role.AbstractUserRoleServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 06.11.2020
 * Time: 14:07
 */
class UserRoleFindClientOrganizationRoleByOrganizationAndUserServiceUnitTest : AbstractUserRoleServiceUnitTest() {

    @Test
    fun `test when noting found`() {
        resetAll()
        val organizationUuid = uuid()
        val userUuid = uuid()
        expect(userRoleRepository.findAllOrganizationClientsRolesByOrganizationAndUser(organizationUuid, userUuid)).andReturn(listOf())
        replayAll()
        val result = userRoleService.findClientOrganizationRoleByOrganizationAndUser(organizationUuid, userUuid)
        assertThat(result).isEmpty()
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val organizationUuid = uuid()
        val user = userCommonTestHelper.buildUser()
        val clientContentManagerRole = commonTestHelper.buildUserClientContentManagerRole(user = user)
        val clientAdminRole = commonTestHelper.buildUserClientAdminRole(user = user)
        expect(userRoleRepository.findAllOrganizationClientsRolesByOrganizationAndUser(organizationUuid, user.uuid)).andReturn(listOf(clientContentManagerRole, clientAdminRole))
        replayAll()
        val result = userRoleService.findClientOrganizationRoleByOrganizationAndUser(organizationUuid, user.uuid)
        assertThat(result).isNotEmpty()
        verifyAll()
    }
}
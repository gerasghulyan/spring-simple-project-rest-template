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
class UserRoleExistsByClientOrganizationAndUserServiceUnitTest : AbstractUserRoleServiceUnitTest() {

    @Test
    fun `test when noting found`() {
        resetAll()
        val clientOrganizationUuid = uuid()
        expect(userRoleRepository.findAllOrganizationClientsByOrganization(clientOrganizationUuid)).andReturn(listOf())
        replayAll()
        val result = userRoleService.existsClientOrganizationRoleByOrganizationAndUser(clientOrganizationUuid, uuid())
        assertThat(result).isFalse()
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val clientOrganizationUuid = uuid()
        val user = userCommonTestHelper.buildUser()
        val clientContentManagerRole = commonTestHelper.buildUserClientContentManagerRole(user = user)
        val clientAdminRole = commonTestHelper.buildUserClientAdminRole(user = user)
        expect(userRoleRepository.findAllOrganizationClientsByOrganization(clientOrganizationUuid)).andReturn(listOf(clientContentManagerRole, clientAdminRole))
        replayAll()
        val result = userRoleService.existsClientOrganizationRoleByOrganizationAndUser(clientOrganizationUuid, user.uuid)
        assertThat(result).isTrue()
        verifyAll()
    }
}
package com.vntana.core.service.user.role.impl

import com.vntana.core.service.user.role.AbstractUserRoleServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 10.11.2020
 * Time: 12:05
 */
class UserRoleFindClientsByOrganizationServiceUnitTest : AbstractUserRoleServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertIllegalArgumentException { userRoleService.findClientsByOrganization(null) }
        assertIllegalArgumentException { userRoleService.findClientsByOrganization(emptyString()) }
        verifyAll()
    }
    
    @Test
    fun test() {
        resetAll()
        val organizationUuid = uuid()
        val roles = listOf(
                commonTestHelper.buildUserClientAdminRole(),
                commonTestHelper.buildUserClientContentManagerRole()
        )
        expect(userRoleRepository.findAllOrganizationClientsByOrganization(organizationUuid)).andReturn(roles)
        replayAll()
        userRoleService.findClientsByOrganization(organizationUuid).let {
            assertThat(it).containsExactlyInAnyOrderElementsOf(roles)
        }
        verifyAll()
    }
}
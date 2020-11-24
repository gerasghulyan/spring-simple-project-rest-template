package com.vntana.core.service.user.role.impl

import com.vntana.core.service.user.role.AbstractUserRoleServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/7/20
 * Time: 4:10 PM
 */
class UserRoleFindAllByClientOrganizationServiceUnitTest : AbstractUserRoleServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { userRoleService.findAllByClientOrganization(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userRoleService.findAllByClientOrganization(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val clientUuid = uuid()
        val roles = listOf(
                commonTestHelper.buildUserClientAdminRole(),
                commonTestHelper.buildUserClientContentManagerRole(),
                commonTestHelper.buildUserClientViewerRole()
        )
        expect(userRoleRepository.findAllByClientOrganization(clientUuid)).andReturn(roles)
        replayAll()
        userRoleService.findAllByClientOrganization(clientUuid).let {
            assertThat(it).containsExactlyInAnyOrderElementsOf(roles)
        }
        verifyAll()
    }
}
package com.vntana.core.service.user.role.impl

import com.vntana.core.service.user.role.AbstractUserRoleServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 4:10 PM
 */
class UserRoleFindAllByOrganizationUuidServiceUnitTest : AbstractUserRoleServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { userRoleService.findAllByOrganizationUuid(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userRoleService.findAllByOrganizationUuid(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val organizationUuid = uuid()
        val roles = listOf(
                commonTestHelper.buildUserOrganizationOwnerRole(),
                commonTestHelper.buildUserOrganizationOwnerRole()
        )
        expect(userRoleRepository.findAllByOrganizationUuid(organizationUuid)).andReturn(roles)
        replayAll()
        userRoleService.findAllByOrganizationUuid(organizationUuid).let {
            assertThat(it).containsExactlyInAnyOrderElementsOf(roles)
        }
        verifyAll()
    }
}
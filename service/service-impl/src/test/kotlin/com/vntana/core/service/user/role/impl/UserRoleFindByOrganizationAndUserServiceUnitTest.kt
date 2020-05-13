package com.vntana.core.service.user.role.impl

import com.vntana.core.service.user.role.AbstractUserRoleServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 6:10 PM
 */
class UserRoleFindByOrganizationAndUserServiceUnitTest : AbstractUserRoleServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { userRoleService.findByOrganizationAndUser(null, uuid()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userRoleService.findByOrganizationAndUser(emptyString(), uuid()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userRoleService.findByOrganizationAndUser(uuid(), null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userRoleService.findByOrganizationAndUser(uuid(), emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val organizationUuid = uuid()
        val userUuid = uuid()
        expect(userRoleRepository.findAllByOrganizationAndUser(organizationUuid, userUuid)).andReturn(Optional.empty())
        replayAll()
        assertThat(userRoleService.findByOrganizationAndUser(organizationUuid, userUuid)).isEmpty
        verifyAll()
    }

    @Test
    fun `test when found`() {
        resetAll()
        val organizationUuid = uuid()
        val userUuid = uuid()
        val role = commonTestHelper.buildUserOrganizationAdminRole()
        expect(userRoleRepository.findAllByOrganizationAndUser(organizationUuid, userUuid)).andReturn(Optional.of(role))
        replayAll()
        userRoleService.findByOrganizationAndUser(organizationUuid, userUuid).let {
            assertThat(it).isPresent
            assertThat(it.get()).isEqualTo(role)
        }
        verifyAll()
    }
}
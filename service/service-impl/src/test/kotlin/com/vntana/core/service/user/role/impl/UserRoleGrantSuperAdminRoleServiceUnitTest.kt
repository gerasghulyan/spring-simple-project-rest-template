package com.vntana.core.service.user.role.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.domain.user.UserSuperAdminRole
import com.vntana.core.service.user.role.AbstractUserRoleServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/27/20
 * Time: 2:16 PM
 */
class UserRoleGrantSuperAdminRoleServiceUnitTest : AbstractUserRoleServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { userRoleService.grantSuperAdminRole(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userRoleService.grantSuperAdminRole(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val user = userCommonTestHelper.buildUser()
        expect(userService.getByUuid(user.uuid)).andReturn(user)
        expect(userRoleRepository.save(isA(UserSuperAdminRole::class.java))).andAnswer { getCurrentArguments()[0] as UserSuperAdminRole }
        replayAll()
        userRoleService.grantSuperAdminRole(user.uuid).let {
            assertThat(it.userRole).isEqualTo(UserRole.SUPER_ADMIN)
            assertThat(it.user).isEqualTo(user)
        }
        verifyAll()
    }
}
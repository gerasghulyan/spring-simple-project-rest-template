package com.vntana.core.service.user.role.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.user.role.AbstractUserRoleServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/27/20
 * Time: 2:14 PM
 */
class UserRoleGrantSuperAdminRoleServiceIntegrationTest : AbstractUserRoleServiceIntegrationTest() {

    @Test
    fun test() {
        val user = userIntegrationTestHelper.persistUser()
        flushAndClear()
        userRoleService.grantSuperAdminRole(user.uuid).let {
            flushAndClear()
            assertThat(it.userRole).isEqualTo(UserRole.SUPER_ADMIN)
            assertThat(it.user).isEqualTo(user)
        }
    }
}
package com.vntana.core.domain.user

import com.vntana.core.domain.AbstractDomainUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/11/19
 * Time: 3:26 PM
 */
class UserDomainGrantSuperAdminRoleUnitTest : AbstractDomainUnitTest() {
    @Test
    fun `test grant when user already has role in given client organization`() {
        val user = User(uuid(), uuid(), uuid())
        user.grantSuperAdminRole()
        assertThatThrownBy { user.grantSuperAdminRole() }
                .isExactlyInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun `test grant`() {
        val user = User(uuid(), uuid(), uuid())
        user.grantSuperAdminRole()
        val role = user.roleOfSuperAdmin().get()
        assertThat(role.user).isEqualTo(user)
        assertThat(role.userRole).isEqualTo(UserRole.SUPER_ADMIN)
    }
}
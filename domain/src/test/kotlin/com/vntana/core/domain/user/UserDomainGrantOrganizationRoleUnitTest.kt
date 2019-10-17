package com.vntana.core.domain.user

import com.vntana.core.domain.AbstractDomainUnitTest
import com.vntana.core.domain.organization.Organization
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/11/19
 * Time: 3:26 PM
 */
class UserDomainGrantOrganizationRoleUnitTest : AbstractDomainUnitTest() {
    @Test
    fun `test grant when user already has role in given client organization`() {
        val organization = Organization()
        val user = User(uuid(), uuid(), uuid())
        user.grantOrganizationRole(organization)
        assertThatThrownBy { user.grantOrganizationRole(organization) }
                .isExactlyInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun `test grant`() {
        val organization = Organization()
        val user = User(uuid(), uuid(), uuid())
        user.grantOrganizationRole(organization)
        val role = user.roleOfOrganization(organization).get()
        assertThat(role.user).isEqualTo(user)
        assertThat(role.organization).isEqualTo(organization)
        assertThat(role.userRole).isEqualTo(UserRole.ORGANIZATION_ADMIN)
    }
}
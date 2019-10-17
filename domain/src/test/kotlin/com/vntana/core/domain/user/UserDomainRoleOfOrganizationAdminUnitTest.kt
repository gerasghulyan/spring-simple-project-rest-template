package com.vntana.core.domain.user

import com.vntana.core.domain.AbstractDomainUnitTest
import com.vntana.core.domain.organization.Organization
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/11/19
 * Time: 3:42 PM
 */
class UserDomainRoleOfOrganizationAdminUnitTest : AbstractDomainUnitTest() {
    @Test
    fun `test roleOf when not found`() {
        val organization = Organization()
        val user = User(uuid(), uuid(), uuid())
        assertThat(user.roleOfOrganization(organization)).isEmpty
    }

    @Test
    fun `test roleOf when found`() {
        val organization = Organization()
        val user = User(uuid(), uuid(), uuid())
        user.grantOrganizationRole(organization)
        val role = user.roleOfOrganization(organization).get()
        assertThat(role.userRole).isEqualTo(UserRole.ORGANIZATION_ADMIN)
        assertThat(role.user).isEqualTo(user)
        assertThat(role.organization).isEqualTo(organization)
    }
}
package com.vntana.core.domain.user

import com.vntana.core.domain.AbstractDomainUnitTest
import com.vntana.core.domain.client.ClientOrganization
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/11/19
 * Time: 3:26 PM
 */
class UserDomainGrantClientRoleUnitTest : AbstractDomainUnitTest() {
    @Test
    fun `test grant when user already has role in given client organization`() {
        val clientOrganization = ClientOrganization()
        val user = User(uuid(), uuid(), uuid())
        user.grantClientRole(clientOrganization, UserRole.CLIENT_ORGANIZATION_ADMIN)
        assertThatThrownBy { user.grantClientRole(clientOrganization, UserRole.CLIENT_ORGANIZATION_ADMIN) }
                .isExactlyInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun `test grant`() {
        val clientOrganization = ClientOrganization()
        val user = User(uuid(), uuid(), uuid())
        user.grantClientRole(clientOrganization, UserRole.CLIENT_ORGANIZATION_ADMIN)
        val role = user.roleOfClient(clientOrganization).get()
        assertThat(role.user).isEqualTo(user)
        assertThat(role.clientOrganization).isEqualTo(clientOrganization)
        assertThat(role.userRole).isEqualTo(UserRole.CLIENT_ORGANIZATION_ADMIN)
    }
}
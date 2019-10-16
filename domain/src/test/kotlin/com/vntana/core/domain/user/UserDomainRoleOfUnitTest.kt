package com.vntana.core.domain.user

import com.vntana.core.domain.AbstractDomainUnitTest
import com.vntana.core.domain.client.ClientOrganization
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/11/19
 * Time: 3:42 PM
 */
class UserDomainRoleOfUnitTest : AbstractDomainUnitTest() {
    @Test
    fun `test roleOf when not found`() {
        val clientOrganization = ClientOrganization()
        val user = User(uuid(), uuid(), uuid())
        assertThat(user.roleOfClient(clientOrganization)).isEmpty
    }

    @Test
    fun `test roleOf when found`() {
        val clientOrganization = ClientOrganization()
        val user = User(uuid(), uuid(), uuid())
        user.grantClientRole(clientOrganization, UserRole.CLIENT_ADMIN)
        val role = user.roleOfClient(clientOrganization).get()
        assertThat(role.userRole).isEqualTo(UserRole.CLIENT_ADMIN)
        assertThat(role.user).isEqualTo(user)
        assertThat(role.clientOrganization).isEqualTo(clientOrganization)
    }
}
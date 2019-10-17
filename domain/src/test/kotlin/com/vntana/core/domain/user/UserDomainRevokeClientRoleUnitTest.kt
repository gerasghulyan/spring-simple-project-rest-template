package com.vntana.core.domain.user

import com.vntana.core.domain.AbstractDomainUnitTest
import com.vntana.core.domain.client.ClientOrganization
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/11/19
 * Time: 3:39 PM
 */
class UserDomainRevokeClientRoleUnitTest : AbstractDomainUnitTest() {
    @Test
    fun `test revoke when user does not have role in client organization`() {
        val clientOrganization = ClientOrganization()
        val user = User(uuid(), uuid(), uuid())
        assertThatThrownBy { user.revokeClientRole(clientOrganization) }
                .isExactlyInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun `test revoke`() {
        val clientOrganization = ClientOrganization()
        val user = User(uuid(), uuid(), uuid())
        user.grantClientRole(clientOrganization, UserRole.CLIENT_ADMIN)
        user.revokeClientRole(clientOrganization)
        assertThat(user.roleOfClient(clientOrganization)).isEmpty
    }
}
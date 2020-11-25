package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 11/24/20
 * Time: 5:44 PM
 */
class TokenInvitationUserToClientFindByTokenServiceIntegrationTest : AbstractTokenInvitationUserServiceIntegrationTest() {

    @Test
    fun `test when token does not exist`() {
        assertThat(tokenInvitationUserService.findByClientInvitationToken(uuid())).isNotPresent
    }
    
    @Test
    fun test() {
        val token = uuid()
        val organization = organizationIntegrationTestHelper.persistOrganization()
        val client = clientOrganizationIntegrationTestHelper.persistClientOrganization(organizationUuid = organization.uuid)
        val userInvitation = invitationUserIntegrationTestHelper.persistInvitationUserToClient(clientUuid = client.uuid)
        flushAndClear()
        val tokenInvitationUser = integrationTestHelper.persistTokenInvitationUserToClient(token = token, invitationUserUuid = userInvitation.uuid)
        flushAndClear()
        tokenInvitationUserService.findByClientInvitationToken(token).let {
            assertThat(it).isPresent
            assertThat(it.get()).isEqualTo(tokenInvitationUser)
        }
    }
}
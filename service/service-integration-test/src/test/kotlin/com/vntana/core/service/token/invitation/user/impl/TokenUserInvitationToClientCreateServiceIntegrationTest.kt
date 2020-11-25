package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 11/20/20
 * Time: 11:06 AM
 */
class TokenUserInvitationToClientCreateServiceIntegrationTest : AbstractTokenInvitationUserServiceIntegrationTest() {

    @Test
    fun test() {
        val organization = organizationIntegrationTestHelper.persistOrganization()
        val client = clientOrganizationIntegrationTestHelper.persistClientOrganization(organizationUuid = organization.uuid)

        val userInvitation = invitationUserIntegrationTestHelper
                .persistInvitationUserToClient(organizationUuid = organization.uuid, clientUuid = client.uuid)
        
        val dto = integrationTestHelper.buildCreateTokenForUserInvitationToClient(
                userInvitationUuid = userInvitation.uuid
        )
        flushAndClear()
        tokenInvitationUserService.createUserInvitationToClients(dto).let { 
            flushAndClear()
            Assertions.assertThat(it.size).isEqualTo(dto.tokens.size)
            Assertions.assertThat(it[0].token).isEqualTo(dto.tokens[0].token)
            Assertions.assertThat(it[0].userInvitation.uuid).isEqualTo(dto.tokens[0].invitationUuid)
        }
    }
}
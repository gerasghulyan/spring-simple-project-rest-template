package com.vntana.core.service.invitation.user.impl.client

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.service.client.dto.CreateClientOrganizationDto
import com.vntana.core.service.invitation.user.AbstractInvitationUserToClientServiceIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 11/10/20
 * Time: 12:44 PM
 */
class CreateUserInvitationToClientServiceIntegrationTest : AbstractInvitationUserToClientServiceIntegrationTest() {

    @Test
    fun test() {
        val organization = organizationIntegrationTestHelper.persistOrganization();
        val dto = integrationInvitationUserTestHelper.buildCreateInvitationUserForClientsDto(
                inviterUserUuid = userIntegrationTestHelper.persistUserWithOwnerRole().uuid,
                clientUuid = clientOrganizationIntegrationTestHelper.persistClientOrganization(uuid(), CreateClientOrganizationDto(uuid(), uuid(), uuid(), organization.uuid)).uuid
        )
        invitationUserToClientService.create(dto).let {
            flushAndClear()
            Assertions.assertThat(it[0].uuid).isNotBlank()
            Assertions.assertThat(it[0].email).isEqualTo(dto.email)
            Assertions.assertThat(it[0].inviterUser.uuid).isEqualTo(dto.inviterUserUuid)
            Assertions.assertThat(it[0].status).isEqualTo(InvitationStatus.INVITED)
        }
    }
}
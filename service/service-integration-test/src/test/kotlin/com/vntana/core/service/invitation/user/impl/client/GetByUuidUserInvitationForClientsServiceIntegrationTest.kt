package com.vntana.core.service.invitation.user.impl.client

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.service.client.dto.CreateClientOrganizationDto
import com.vntana.core.service.invitation.user.AbstractInvitationUserToClientServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 11/19/20
 * Time: 6:41 PM
 */
class GetByUuidUserInvitationForClientsServiceIntegrationTest : AbstractInvitationUserToClientServiceIntegrationTest() {

    @Test
    fun test() {
        val organizationUuid = organizationIntegrationTestHelper.persistOrganization().uuid;
        val clientUuid = clientOrganizationIntegrationTestHelper.persistClientOrganization(uuid(), CreateClientOrganizationDto(uuid(), uuid(), uuid(), organizationUuid)).uuid
        val invitation = integrationInvitationUserTestHelper.persistInvitationUserToClient(clientUuid = clientUuid, organizationUuid = organizationUuid)
        flushAndClear()

        invitationUserToClientService.getByUuid(invitation.uuid).let {
            flushAndClear()
            assertThat(it.uuid).isNotBlank()
            assertThat(it.email).isEqualTo(invitation.email)
            assertThat(it.status).isEqualTo(InvitationStatus.INVITED)
        }
    }
}
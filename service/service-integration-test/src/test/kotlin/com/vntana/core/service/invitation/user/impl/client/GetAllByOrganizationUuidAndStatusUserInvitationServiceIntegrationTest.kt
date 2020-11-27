package com.vntana.core.service.invitation.user.impl.client

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.service.invitation.user.AbstractInvitationUserToClientServiceIntegrationTest
import com.vntana.core.service.invitation.user.dto.GetAllByOrganizationUuidAndStatusInvitationUsersDto
import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 11/27/20
 * Time: 9:41 AM
 */
class GetAllByOrganizationUuidAndStatusUserInvitationServiceIntegrationTest : AbstractInvitationUserToClientServiceIntegrationTest() {

    @Test
    fun test() {
        val page = 0
        val size = 2
        val organization = organizationIntegrationTestHelper.persistOrganization()
        val client1 = clientOrganizationIntegrationTestHelper.persistClientOrganization(organizationUuid = organization.uuid)
        val client2 = clientOrganizationIntegrationTestHelper.persistClientOrganization(organizationUuid = organization.uuid)

        organization.grantClientOrganization(client1)
        organization.grantClientOrganization(client2)

        val userInvitation1 = integrationInvitationUserTestHelper.persistInvitationUserToClient(clientUuid = client1.uuid, organizationUuid = organization.uuid)
        val userInvitation2 = integrationInvitationUserTestHelper.persistInvitationUserToClient(clientUuid = client2.uuid, organizationUuid = organization.uuid)

        val dto = GetAllByOrganizationUuidAndStatusInvitationUsersDto(page, size, organization.uuid, InvitationStatus.INVITED)

        invitationUserToClientService.getAllByOrganizationUuidAndStatus(dto).let {
            Assertions.assertThat(it.size).isEqualTo(2)
            Assertions.assertThat(it).containsExactlyInAnyOrder(userInvitation1, userInvitation2)
        }
    }
}
package com.vntana.core.service.invitation.user.impl.organization

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.service.invitation.user.AbstractInvitationUserToOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.stream.Collectors

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:43 PM
 */
class InvitationUserToOrganizationGetAllByOrganizationUuidAndStatusServiceIntegrationTest : AbstractInvitationUserToOrganizationServiceIntegrationTest() {

    @Test
    fun `test when empty`() {
        invitationUserToOrganizationService.getAllByOrganizationUuidAndStatus(integrationInvitationUserTestHelper.buildGetAllByOrganizationUuidAndStatusInvitationUsersDto()).run {
            assertThat(totalElements).isEqualTo(0)
        }
    }

    @Test
    fun test() {
        val organizationUuid = organizationIntegrationTestHelper.persistOrganization().uuid
        val invitation1 = integrationInvitationUserTestHelper.persistInvitationUserToOrganization(
                inviterUserUuid = userIntegrationTestHelper.persistUserWithOwnerRole().uuid,
                organizationUuid = organizationUuid
        )
        val invitation2 = integrationInvitationUserTestHelper.persistInvitationUserToOrganization(
                inviterUserUuid = userIntegrationTestHelper.persistUserWithOwnerRole().uuid,
                organizationUuid = organizationUuid
        )
        val invitation3 = integrationInvitationUserTestHelper.persistInvitationUserToOrganization(
                inviterUserUuid = userIntegrationTestHelper.persistUserWithOwnerRole().uuid
        )
        integrationInvitationUserTestHelper.updateInvitationUserToOrganizationStatus(uuid = invitation3.uuid, status = InvitationStatus.REJECTED)
        flushAndClear()
        invitationUserToOrganizationService.getAllByOrganizationUuidAndStatus(integrationInvitationUserTestHelper.buildGetAllByOrganizationUuidAndStatusInvitationUsersDto(organizationUuid = organizationUuid)).run {
            assertThat(totalElements).isEqualTo(2)
            val list = get().collect(Collectors.toList())
            assertThat(list).containsExactlyInAnyOrder(invitation1, invitation2)
            assertThat(list).doesNotContain(invitation3)
        }
    }
}
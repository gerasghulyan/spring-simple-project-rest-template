package com.vntana.core.service.invitation.user.impl

import com.vntana.core.service.invitation.user.AbstractInvitationUserServiceIntegrationTest
import com.vntana.core.service.organization.exception.OrganizationNotFoundForUuidException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 2:31 PM
 */
class InvitationUserGetByEmailAndOrganizationUuidAndStatusServiceIntegrationTest : AbstractInvitationUserServiceIntegrationTest() {
    
    @Test
    fun test() {
        val email = uuid()
        val organizationUuid = organizationIntegrationTestHelper.persistOrganization().uuid
        val persistUserUuid1 = userIntegrationTestHelper.persistUser().uuid
        val persistUserUuid2 = userIntegrationTestHelper.persistUser().uuid
        val invitation1 = integrationInvitationUserTestHelper.persistInvitationUser(email = email, organizationUuid = organizationUuid, inviterUserUuid = persistUserUuid1)
        val invitation2 = integrationInvitationUserTestHelper.persistInvitationUser(email = email, organizationUuid = organizationUuid, inviterUserUuid = persistUserUuid2)
        val invitation3 = integrationInvitationUserTestHelper.persistInvitationUser(email = email, organizationUuid = organizationUuid, inviterUserUuid = persistUserUuid1)
        val invitation4 = integrationInvitationUserTestHelper.persistInvitationUser(email = email, organizationUuid = organizationUuid, inviterUserUuid = persistUserUuid2)
        val invitation5 = integrationInvitationUserTestHelper.persistInvitationUser(email = email, organizationUuid = organizationUuid, inviterUserUuid = persistUserUuid1)
        integrationInvitationUserTestHelper.updateInvitationUserStatus(invitation3.uuid)
        integrationInvitationUserTestHelper.updateInvitationUserStatus(invitation5.uuid)
        flushAndClear()
        invitationUserService.getByEmailAndOrganizationUuidAndStatusOrderByCreatedDesc(
                integrationInvitationUserTestHelper.buildGetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto(
                        email, organizationUuid
                )
        ).let { invitations ->
            assertThat(invitations).isNotEmpty
            assertThat(invitations).containsExactlyInAnyOrder(invitation4, invitation2, invitation1)
            assertThat(invitations).doesNotContainAnyElementsOf(listOf(invitation5, invitation3))
        }
    }
}
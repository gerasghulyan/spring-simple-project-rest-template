package com.vntana.core.service.invitation.user.impl.organization

import com.vntana.core.service.invitation.user.AbstractInvitationUserToOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 2:53 PM
 */
class InvitationUserToOrganizationGetAllByInviterUserUuidServiceIntegrationTest : AbstractInvitationUserToOrganizationServiceIntegrationTest() {
    
    @Test
    fun test() {
        val inviterUserUuid = userIntegrationTestHelper.persistUserWithOwnerRole().uuid
        val invitation1 = integrationInvitationUserTestHelper.persistInvitationUserToOrganization(inviterUserUuid = inviterUserUuid)
        val invitation2 = integrationInvitationUserTestHelper.persistInvitationUserToOrganization(inviterUserUuid = inviterUserUuid)
        flushAndClear()
        invitationUserToOrganizationService.getAllByInviterUserUuid(inviterUserUuid).let { list ->
            assertThat(list.isEmpty()).isFalse()
            assertThat(list).containsExactlyInAnyOrder(invitation1, invitation2)
        }
    }
}
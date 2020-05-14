package com.vntana.core.service.invitation.user.impl

import com.vntana.core.service.invitation.user.AbstractInvitationUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 2:53 PM
 */
class InvitationUserGetAllByInviterUserUuidServiceIntegrationTest : AbstractInvitationUserServiceIntegrationTest() {
    
    @Test
    fun test() {
        val inviterUserUuid = userIntegrationTestHelper.persistUser().uuid
        val invitation1 = integrationInvitationUserTestHelper.persistInvitationUser(inviterUserUuid = inviterUserUuid)
        val invitation2 = integrationInvitationUserTestHelper.persistInvitationUser(inviterUserUuid = inviterUserUuid)
        flushAndClear()
        invitationUserService.getAllByInviterUserUuid(inviterUserUuid).let { list ->
            assertThat(list.isEmpty()).isFalse()
            assertThat(list).containsExactlyInAnyOrder(invitation1, invitation2)
        }
    }
}
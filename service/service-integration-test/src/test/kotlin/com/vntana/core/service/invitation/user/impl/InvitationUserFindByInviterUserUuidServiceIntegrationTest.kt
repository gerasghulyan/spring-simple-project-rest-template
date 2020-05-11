package com.vntana.core.service.invitation.user.impl

import com.vntana.core.service.invitation.user.AbstractInvitationUserServiceIntegrationTest
import com.vntana.core.service.user.exception.UserNotFoundForUuidException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 2:53 PM
 */
class InvitationUserFindByInviterUserUuidServiceIntegrationTest : AbstractInvitationUserServiceIntegrationTest() {

    @Test
    fun `test inviter user not found`() {
        val inviterUserUuid = uuid()
        assertThatThrownBy { invitationUserService.findByInviterUserUuid(inviterUserUuid) }.isExactlyInstanceOf(UserNotFoundForUuidException::class.java)
    }

    @Test
    fun test() {
        val inviterUserUuid = userIntegrationTestHelper.persistUser().uuid
        val invitation1 = integrationUserTestHelper.persistInvitationUser(inviterUserUuid = inviterUserUuid)
        val invitation2 = integrationUserTestHelper.persistInvitationUser(inviterUserUuid = inviterUserUuid)
        flushAndClear()
        invitationUserService.findByInviterUserUuid(inviterUserUuid).let { list ->
            assertThat(list.isEmpty()).isFalse()
            assertThat(list).containsExactlyInAnyOrder(invitation1, invitation2)
        }
    }
}
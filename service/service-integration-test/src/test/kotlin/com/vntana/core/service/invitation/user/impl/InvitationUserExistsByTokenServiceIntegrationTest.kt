package com.vntana.core.service.invitation.user.impl

import com.vntana.core.service.invitation.user.AbstractInvitationUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 8:36 PM
 */
class InvitationUserExistsByTokenServiceIntegrationTest : AbstractInvitationUserServiceIntegrationTest() {

    @Test
    fun `test not found`() {
        assertThat(invitationUserService.existsByToken(uuid())).isFalse()
    }

    @Test
    fun test() {
        val invitationUser = integrationInvitationUserTestHelper.persistInvitationUser()
        val token = tokenInvitationUserIntegrationTestHelper.persistTokenInvitationUser(invitationUserUuid = invitationUser.uuid).token
        flushAndClear()
        assertThat(invitationUserService.existsByToken(token)).isTrue()
    }
}
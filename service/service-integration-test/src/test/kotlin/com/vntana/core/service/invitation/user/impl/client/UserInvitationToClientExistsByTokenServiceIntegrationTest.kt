package com.vntana.core.service.invitation.user.impl.client

import com.vntana.core.service.invitation.user.AbstractInvitationUserToClientServiceIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 11/26/20
 * Time: 4:02 PM
 */
class UserInvitationToClientExistsByTokenServiceIntegrationTest : AbstractInvitationUserToClientServiceIntegrationTest() {

    @Test
    fun `test not found`() {
        Assertions.assertThat(invitationUserToClientService.existsByToken(uuid())).isFalse()
    }

    @Test
    fun test() {
        val invitationUser = integrationInvitationUserTestHelper.persistUserInvitationToClient()
        val token = tokenInvitationUserIntegrationTestHelper.persistTokenInvitationUserToClient(invitationUserUuid = invitationUser.uuid).token
        flushAndClear()
        Assertions.assertThat(invitationUserToClientService.existsByToken(token)).isTrue()
    }
}
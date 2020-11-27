package com.vntana.core.service.invitation.user.impl.client

import com.vntana.core.service.invitation.user.AbstractInvitationUserToClientServiceIntegrationTest
import com.vntana.core.service.invitation.user.exception.InvitationUserNotFoundForTokenException
import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 11/27/20
 * Time: 9:33 AM
 */
class GetByTokenUserInvitationServiceIntegrationTest : AbstractInvitationUserToClientServiceIntegrationTest() {

    @Test
    fun `test not found`() {
        Assertions.assertThatThrownBy { invitationUserToClientService.getByToken(uuid()) }
                .isExactlyInstanceOf(InvitationUserNotFoundForTokenException::class.java)
    }

    @Test
    fun test() {
        val invitationUser = integrationInvitationUserTestHelper.persistUserInvitationToClient()
        val token = tokenInvitationUserIntegrationTestHelper.persistTokenInvitationUserToClient(invitationUserUuid = invitationUser.uuid).token
        flushAndClear()
        invitationUserToClientService.getByToken(token).let {
            Assertions.assertThat(it).isNotNull
            Assertions.assertThat(it).isEqualTo(invitationUser)
        }
    }
}
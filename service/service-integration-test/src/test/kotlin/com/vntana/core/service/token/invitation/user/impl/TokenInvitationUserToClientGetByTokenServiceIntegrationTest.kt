package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceIntegrationTest
import com.vntana.core.service.token.invitation.user.exception.TokenInvitationUserNotFoundForTokenException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Diana Gevorgyan.
 * Date: 5/14/20
 * Time: 6:25 PM
 */
class TokenInvitationUserToClientGetByTokenServiceIntegrationTest : AbstractTokenInvitationUserServiceIntegrationTest() {

    @Test
    fun `test getByToken not found`() {
        assertThatThrownBy { tokenInvitationUserService.getByClientInvitationToken(uuid()) }
                .isExactlyInstanceOf(TokenInvitationUserNotFoundForTokenException::class.java)
    }

    @Test
    fun `test getByToken`() {
        val token = uuid()
        val invitationUser = invitationUserIntegrationTestHelper.persistUserInvitationToClient()
        val tokenInvitationUser = integrationTestHelper.persistTokenInvitationUserToClient(token = token, invitationUserUuid = invitationUser.uuid)
        flushAndClear()
        tokenInvitationUserService.getByClientInvitationToken(token).let {
            assertThat(it).isEqualTo(tokenInvitationUser)
        }
    }
}
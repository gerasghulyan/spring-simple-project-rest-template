package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceIntegrationTest
import com.vntana.core.service.token.invitation.user.exception.TokenInvitationUserNotFoundForTokenException
import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 11/27/20
 * Time: 11:34 AM
 */
class TokenUserInvitationIsExpiredServiceIntegrationTest : AbstractTokenInvitationUserServiceIntegrationTest() {

    @Test
    fun `test when does not exist`() {
        Assertions.assertThatThrownBy { tokenInvitationUserService.isInvitationToClientExpired(uuid()) }
                .isExactlyInstanceOf(TokenInvitationUserNotFoundForTokenException::class.java)
    }

    @Test
    fun `test when invitation token does not expired`() {
        val token = integrationTestHelper.persistTokenInvitationUserToClient().token
        flushAndClear()
        Assertions.assertThat(tokenInvitationUserService.isInvitationToClientExpired(token)).isFalse()
    }

    @Test
    fun `test when invitation token has been expired`() {
        val tokenInvitationUser = integrationTestHelper.persistTokenInvitationUserToClient()
        tokenInvitationUser.expire()
        flushAndClear()
        Assertions.assertThat(tokenInvitationUserService.isInvitationToClientExpired(tokenInvitationUser.token)).isTrue()
    }
}
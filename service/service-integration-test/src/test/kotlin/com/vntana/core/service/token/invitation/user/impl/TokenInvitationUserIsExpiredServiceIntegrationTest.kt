package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceIntegrationTest
import com.vntana.core.service.token.invitation.user.exception.TokenInvitationUserNotFoundForTokenException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 7:47 PM
 */
class TokenInvitationUserIsExpiredServiceIntegrationTest : AbstractTokenInvitationUserServiceIntegrationTest() {

    @Test
    fun `test when does not exist`() {
        assertThatThrownBy { tokenInvitationUserService.isExpired(uuid()) }
                .isExactlyInstanceOf(TokenInvitationUserNotFoundForTokenException::class.java)
    }

    @Test
    fun `test when invitation token does not expired`() {
        val token = integrationTestHelper.persistTokenInvitationUserToOrganization().token
        flushAndClear()
        assertThat(tokenInvitationUserService.isExpired(token)).isFalse()
    }

    @Test
    fun `test when invitation token has been expired`() {
        val tokenInvitationUser = integrationTestHelper.persistTokenInvitationUserToOrganization()
        tokenInvitationUser.expire()
        flushAndClear()
        assertThat(tokenInvitationUserService.isExpired(tokenInvitationUser.token)).isTrue()
    }
}
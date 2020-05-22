package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceUnitTest
import com.vntana.core.service.token.invitation.user.exception.TokenInvitationUserNotFoundForTokenException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 6:17 PM
 */
class TokenInvitationUserGetByTokenServiceUnitTest : AbstractTokenInvitationUserServiceUnitTest() {

    @Test
    fun `test when not found`() {
        resetAll()
        val token = uuid()
        expect(tokenRepository.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { tokenInvitationUserService.getByToken(token) }
                .isExactlyInstanceOf(TokenInvitationUserNotFoundForTokenException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val tokenInvitationUser = commonTestHelper.buildTokenInvitationUser()
        val token = tokenInvitationUser.token
        expect(tokenRepository.findByToken(token)).andReturn(Optional.of(tokenInvitationUser))
        replayAll()
        assertThat(tokenInvitationUserService.getByToken(token)).isEqualTo(tokenInvitationUser)
        verifyAll()
    }
}
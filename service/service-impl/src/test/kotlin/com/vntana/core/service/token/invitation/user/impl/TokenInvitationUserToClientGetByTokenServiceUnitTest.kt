package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceUnitTest
import com.vntana.core.service.token.invitation.user.exception.TokenInvitationUserNotFoundForTokenException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Diana Gevorgyan.
 * Date: 5/14/20
 * Time: 6:17 PM
 */
class TokenInvitationUserToClientGetByTokenServiceUnitTest : AbstractTokenInvitationUserServiceUnitTest() {

    @Test
    fun `test when not found`() {
        resetAll()
        val token = uuid()
        expect(tokenUserInvitationToOrganizationClientRepository.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { tokenInvitationUserService.getByClientInvitationToken(token) }
                .isExactlyInstanceOf(TokenInvitationUserNotFoundForTokenException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val tokenInvitationUser = commonTestHelper.buildTokenInvitationUserToClient()
        val token = tokenInvitationUser.token
        expect(tokenUserInvitationToOrganizationClientRepository.findByToken(token)).andReturn(Optional.of(tokenInvitationUser))
        replayAll()
        assertThat(tokenInvitationUserService.getByClientInvitationToken(token)).isEqualTo(tokenInvitationUser)
        verifyAll()
    }
}
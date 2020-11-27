package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceUnitTest
import com.vntana.core.service.token.invitation.user.exception.TokenInvitationUserNotFoundForTokenException
import org.assertj.core.api.Assertions.*
import org.easymock.EasyMock.*
import org.junit.Test
import java.util.*

/**
 * Created by Diana Gevorgyan
 * Date: 11/27/20
 * Time: 11:36 AM
 */
class TokenUserInvitationIsExpiredServiceUnitTest : AbstractTokenInvitationUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { tokenInvitationUserService.isInvitationToClientExpired(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenInvitationUserService.isInvitationToClientExpired(emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when does not expired`() {
        resetAll()
        val token = uuid()
        expect(tokenUserInvitationToOrganizationClientRepository.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { tokenInvitationUserService.isInvitationToClientExpired(token) }
                .isExactlyInstanceOf(TokenInvitationUserNotFoundForTokenException::class.java)
        verifyAll()
    }

    @Test
    fun `test when invitation token does not exist`() {
        resetAll()
        val userInvitationToken = commonTestHelper.buildTokenInvitationUserToClient()
        val token = userInvitationToken.token
        expect(tokenUserInvitationToOrganizationClientRepository.findByToken(token)).andReturn(Optional.of(userInvitationToken))
        replayAll()
        assertThat(tokenInvitationUserService.isInvitationToClientExpired(token)).isFalse()
        verifyAll()
    }

    @Test
    fun `test when invitation token has been expired`() {
        resetAll()
        val userInvitationToken = commonTestHelper.buildTokenInvitationUserToClient()
        userInvitationToken.expire()
        val token = userInvitationToken.token
        expect(tokenUserInvitationToOrganizationClientRepository.findByToken(token)).andReturn(Optional.of(userInvitationToken))
        replayAll()
        assertThat(tokenInvitationUserService.isInvitationToClientExpired(token)).isTrue()
        verifyAll()
    }
}
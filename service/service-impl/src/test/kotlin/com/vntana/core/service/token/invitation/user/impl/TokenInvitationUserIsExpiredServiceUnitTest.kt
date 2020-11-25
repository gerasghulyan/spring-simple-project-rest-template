package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceUnitTest
import com.vntana.core.service.token.invitation.user.exception.TokenInvitationUserNotFoundForTokenException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 7:47 PM
 */
class TokenInvitationUserIsExpiredServiceUnitTest : AbstractTokenInvitationUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { tokenInvitationUserService.isExists(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenInvitationUserService.isExists(emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when does not expired`() {
        resetAll()
        val token = uuid()
        expect(tokenInvitationToOrganizationRepository.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { tokenInvitationUserService.isExpired(token) }
                .isExactlyInstanceOf(TokenInvitationUserNotFoundForTokenException::class.java)
        verifyAll()
    }

    @Test
    fun `test when invitation token does not exist`() {
        resetAll()
        val tokenInvitationUser = commonTestHelper.buildTokenInvitationUserToOrganization()
        val token = tokenInvitationUser.token
        expect(tokenInvitationToOrganizationRepository.findByToken(token)).andReturn(Optional.of(tokenInvitationUser))
        replayAll()
        assertThat(tokenInvitationUserService.isExpired(token)).isFalse()
        verifyAll()
    }

    @Test
    fun `test when invitation token has been expired`() {
        resetAll()
        val tokenInvitationUser = commonTestHelper.buildTokenInvitationUserToOrganization()
        tokenInvitationUser.expire()
        val token = tokenInvitationUser.token
        expect(tokenInvitationToOrganizationRepository.findByToken(token)).andReturn(Optional.of(tokenInvitationUser))
        replayAll()
        assertThat(tokenInvitationUserService.isExpired(token)).isTrue()
        verifyAll()
    }
}
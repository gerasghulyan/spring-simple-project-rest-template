package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 2:24 PM
 */
class TokenInvitationUserFindByTokenServiceUnitTest : AbstractTokenInvitationUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        assertThatThrownBy { tokenInvitationUserService.findByToken(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenInvitationUserService.findByToken(emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        replayAll()
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val token = uuid()
        expect(tokenRepository.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        assertThat(tokenInvitationUserService.findByToken(token)).isEmpty
        verifyAll()
    }

    @Test
    fun `test when found`() {
        resetAll()
        val token = uuid()
        val tokenInvitationUser = commonTestHelper.buildTokenInvitationUser()
        expect(tokenRepository.findByToken(token)).andReturn(Optional.of(tokenInvitationUser))
        replayAll()
        assertThat(tokenInvitationUserService.findByToken(token)).hasValue(tokenInvitationUser)
        verifyAll()
    }
}
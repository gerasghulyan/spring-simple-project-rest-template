package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Diana Gevorgyan.
 * Date: 5/14/20
 * Time: 2:24 PM
 */
class TokenInvitationUserToClientFindByTokenServiceUnitTest : AbstractTokenInvitationUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        assertThatThrownBy { tokenInvitationUserService.findByClientInvitationToken(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenInvitationUserService.findByClientInvitationToken(emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        replayAll()
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val token = uuid()
        expect(tokenUserInvitationToOrganizationClientRepository.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        assertThat(tokenInvitationUserService.findByClientInvitationToken(token)).isEmpty
        verifyAll()
    }

    @Test
    fun `test when found`() {
        resetAll()
        val token = uuid()
        val userInvitationToken = commonTestHelper.buildTokenInvitationUserToClient()
        expect(tokenUserInvitationToOrganizationClientRepository.findByToken(token)).andReturn(Optional.of(userInvitationToken))
        replayAll()
        assertThat(tokenInvitationUserService.findByClientInvitationToken(token)).hasValue(userInvitationToken)
        verifyAll()
    }
}
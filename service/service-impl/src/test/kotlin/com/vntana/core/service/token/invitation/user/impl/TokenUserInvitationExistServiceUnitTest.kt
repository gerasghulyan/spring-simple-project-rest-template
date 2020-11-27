package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceUnitTest
import org.assertj.core.api.Assertions.*
import org.easymock.EasyMock.*
import org.junit.Test
import java.util.*

/**
 * Created by Diana Gevorgyan
 * Date: 11/27/20
 * Time: 11:39 AM
 */
class TokenUserInvitationExistServiceUnitTest: AbstractTokenInvitationUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { tokenInvitationUserService.existsInvitationToClientByToken(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenInvitationUserService.existsInvitationToClientByToken(emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when does not exist`() {
        resetAll()
        val token = uuid()
        expect(tokenUserInvitationToOrganizationClientRepository.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        assertThat(tokenInvitationUserService.existsInvitationToClientByToken(token)).isFalse()
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val userInvitationToken = commonTestHelper.buildTokenInvitationUserToClient()
        val token = userInvitationToken.token
        expect(tokenUserInvitationToOrganizationClientRepository.findByToken(token)).andReturn(Optional.of(userInvitationToken))
        replayAll()
        assertThat(tokenInvitationUserService.existsInvitationToClientByToken(token)).isTrue()
        verifyAll()
    }
}
package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceUnitTest
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
class TokenInvitationUserIsExistsServiceUnitTest : AbstractTokenInvitationUserServiceUnitTest() {

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
    fun `test when does not exist`() {
        resetAll()
        val token = uuid()
        expect(tokenInvitationToOrganizationRepository.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        assertThat(tokenInvitationUserService.isExists(token)).isFalse()
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val tokenInvitationUser = commonTestHelper.buildTokenInvitationUser()
        val token = tokenInvitationUser.token
        expect(tokenInvitationToOrganizationRepository.findByToken(token)).andReturn(Optional.of(tokenInvitationUser))
        replayAll()
        assertThat(tokenInvitationUserService.isExists(token)).isTrue()
        verifyAll()
    }
}
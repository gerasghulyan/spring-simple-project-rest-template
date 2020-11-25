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
class TokenInvitationUserToOrganizationFindByTokenServiceUnitTest : AbstractTokenInvitationUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        assertThatThrownBy { tokenInvitationUserService.findByOrganizationInvitationToken(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenInvitationUserService.findByOrganizationInvitationToken(emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        replayAll()
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val token = uuid()
        expect(tokenInvitationToOrganizationRepository.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        assertThat(tokenInvitationUserService.findByOrganizationInvitationToken(token)).isEmpty
        verifyAll()
    }

    @Test
    fun `test when found`() {
        resetAll()
        val token = uuid()
        val tokenInvitationUser = commonTestHelper.buildTokenInvitationUserToOrganization()
        expect(tokenInvitationToOrganizationRepository.findByToken(token)).andReturn(Optional.of(tokenInvitationUser))
        replayAll()
        assertThat(tokenInvitationUserService.findByOrganizationInvitationToken(token)).hasValue(tokenInvitationUser)
        verifyAll()
    }
}
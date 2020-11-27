package com.vntana.core.service.invitation.user.impl.client

import com.vntana.core.service.invitation.user.AbstractUserInvitationToClientServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Diana Gevorgyan
 * Date: 11/27/20
 * Time: 8:33 AM
 */
class ExistsByTokenUserInvitationToClientServiceUnitTest : AbstractUserInvitationToClientServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { invitationUserToClientService.existsByToken(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { invitationUserToClientService.existsByToken(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val token = uuid()
        expect(invitationOrganizationClientUserRepository.findUserInvitationByToken(token)).andReturn(Optional.empty())
        replayAll()
        assertThat(invitationUserToClientService.existsByToken(token)).isFalse()
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val token = uuid()
        val userInvitation = commonTestHelper.buildInvitationOrganizationClientUser()
        expect(invitationOrganizationClientUserRepository.findUserInvitationByToken(token)).andReturn(Optional.of(userInvitation))
        replayAll()
        assertThat(invitationUserToClientService.existsByToken(token)).isTrue()
        verifyAll()
    }
}
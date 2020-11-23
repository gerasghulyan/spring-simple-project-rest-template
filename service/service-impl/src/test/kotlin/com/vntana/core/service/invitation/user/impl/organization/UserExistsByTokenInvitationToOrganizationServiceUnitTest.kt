package com.vntana.core.service.invitation.user.impl.organization

import com.vntana.core.service.invitation.user.AbstractUserInvitationToOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 8:38 PM
 */
class UserExistsByTokenInvitationToOrganizationServiceUnitTest : AbstractUserInvitationToOrganizationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { invitationUserToOrganizationService.existsByToken(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { invitationUserToOrganizationService.existsByToken(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val token = uuid()
        expect(invitationOrganizationUserRepository.findByTokenInvitationUser(token)).andReturn(Optional.empty())
        replayAll()
        assertThat(invitationUserToOrganizationService.existsByToken(token)).isFalse()
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val token = uuid()
        val invitationUser = commonTestHelper.buildInvitationUserToOrganization()
        expect(invitationOrganizationUserRepository.findByTokenInvitationUser(token)).andReturn(Optional.of(invitationUser))
        replayAll()
        assertThat(invitationUserToOrganizationService.existsByToken(token)).isTrue()
        verifyAll()
    }
}
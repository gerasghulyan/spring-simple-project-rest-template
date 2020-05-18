package com.vntana.core.service.invitation.user.impl

import com.vntana.core.service.invitation.user.AbstractInvitationUserServiceUnitTest
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
class InvitationUserExistsByTokenServiceUnitTest : AbstractInvitationUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { invitationUserService.existsByToken(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { invitationUserService.existsByToken(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val token = uuid()
        expect(invitationUserRepository.findByTokenInvitationUser(token)).andReturn(Optional.empty())
        replayAll()
        assertThat(invitationUserService.existsByToken(token)).isFalse()
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val token = uuid()
        val invitationUser = commonTestHelper.buildInvitationUser()
        expect(invitationUserRepository.findByTokenInvitationUser(token)).andReturn(Optional.of(invitationUser))
        replayAll()
        assertThat(invitationUserService.existsByToken(token)).isTrue()
        verifyAll()
    }
}
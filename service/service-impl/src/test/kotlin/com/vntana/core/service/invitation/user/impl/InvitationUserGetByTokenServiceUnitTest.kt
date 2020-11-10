package com.vntana.core.service.invitation.user.impl

import com.vntana.core.service.invitation.user.AbstractInvitationUserServiceUnitTest
import com.vntana.core.service.invitation.user.exception.InvitationUserNotFoundForTokenException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 8:30 PM
 */
class InvitationUserGetByTokenServiceUnitTest : AbstractInvitationUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { invitationUserService.getByToken(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { invitationUserService.getByToken(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val token = uuid()
        expect(invitationOrganizationUserRepository.findByTokenInvitationUser(token)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { invitationUserService.getByToken(token) }
                .isExactlyInstanceOf(InvitationUserNotFoundForTokenException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val token = uuid()
        val invitationUser = commonTestHelper.buildInvitationUser()
        expect(invitationOrganizationUserRepository.findByTokenInvitationUser(token)).andReturn(Optional.of(invitationUser))
        replayAll()
        invitationUserService.getByToken(token).let {
            assertThat(it).isNotNull
            assertThat(it).isEqualTo(invitationUser)
        }
        verifyAll()
    }
}
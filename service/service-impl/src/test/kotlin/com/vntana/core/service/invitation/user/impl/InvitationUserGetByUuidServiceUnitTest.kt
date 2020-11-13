package com.vntana.core.service.invitation.user.impl

import com.vntana.core.service.invitation.user.AbstractInvitationUserServiceUnitTest
import com.vntana.core.service.invitation.user.exception.InvitationUserNotFoundForUuidException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:43 PM
 */
class InvitationUserGetByUuidServiceUnitTest : AbstractInvitationUserServiceUnitTest() {
    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { invitationUserService.getByUuid(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { invitationUserService.getByUuid(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val uuid = uuid()
        expect(invitationOrganizationUserRepository.findByUuid(uuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { invitationUserService.getByUuid(uuid) }
                .isExactlyInstanceOf(InvitationUserNotFoundForUuidException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val invitationUser = commonTestHelper.buildInvitationUser()
        val uuid = invitationUser.uuid
        expect(invitationOrganizationUserRepository.findByUuid(uuid)).andReturn(Optional.of(invitationUser))
        replayAll()
        assertThat(invitationUserService.getByUuid(uuid)).isEqualTo(invitationUser)
        verifyAll()
    }
}
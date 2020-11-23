package com.vntana.core.service.invitation.user.impl.organization

import com.vntana.core.service.invitation.user.AbstractUserInvitationToOrganizationServiceUnitTest
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
class UserGetByUuidInvitationToOrganizationServiceUnitTest : AbstractUserInvitationToOrganizationServiceUnitTest() {
    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { invitationUserToOrganizationService.getByUuid(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { invitationUserToOrganizationService.getByUuid(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val uuid = uuid()
        expect(invitationOrganizationUserRepository.findByUuid(uuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { invitationUserToOrganizationService.getByUuid(uuid) }
                .isExactlyInstanceOf(InvitationUserNotFoundForUuidException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val invitationUser = commonTestHelper.buildInvitationUserToOrganization()
        val uuid = invitationUser.uuid
        expect(invitationOrganizationUserRepository.findByUuid(uuid)).andReturn(Optional.of(invitationUser))
        replayAll()
        assertThat(invitationUserToOrganizationService.getByUuid(uuid)).isEqualTo(invitationUser)
        verifyAll()
    }
}
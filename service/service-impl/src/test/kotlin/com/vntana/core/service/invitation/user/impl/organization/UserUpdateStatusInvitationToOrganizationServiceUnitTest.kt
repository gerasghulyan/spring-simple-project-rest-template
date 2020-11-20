package com.vntana.core.service.invitation.user.impl.organization

import com.vntana.core.domain.invitation.user.InvitationOrganizationUser
import com.vntana.core.service.invitation.user.AbstractUserInvitationToOrganizationServiceUnitTest
import com.vntana.core.service.invitation.user.exception.InvitationUserNotFoundForUuidException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:44 PM
 */
class UserUpdateStatusInvitationToOrganizationServiceUnitTest : AbstractUserInvitationToOrganizationServiceUnitTest() {
    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildUpdateInvitationUserStatusDto(uuid = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildUpdateInvitationUserStatusDto(uuid = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildUpdateInvitationUserStatusDto(status = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test update`() {
        val dto = commonTestHelper.buildUpdateInvitationUserStatusDto()
        val invitationUser = commonTestHelper.buildInvitationUserToOrganization()
        resetAll()
        expect(invitationOrganizationUserRepository.findByUuid(dto.uuid)).andReturn(Optional.of(invitationUser))
        expect(invitationOrganizationUserRepository.save(EasyMock.isA(InvitationOrganizationUser::class.java))).andReturn(invitationUser)
        replayAll()
        assertThat(invitationUserToOrganizationService.updateStatus(dto)).isEqualTo(invitationUser)
        verifyAll()
    }

    @Test
    fun `test update invitation organization not found`() {
        val dto = commonTestHelper.buildUpdateInvitationUserStatusDto()
        resetAll()
        expect(invitationOrganizationUserRepository.findByUuid(dto.uuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { invitationUserToOrganizationService.updateStatus(dto) }.isExactlyInstanceOf(InvitationUserNotFoundForUuidException::class.java)
        verifyAll()
    }
}
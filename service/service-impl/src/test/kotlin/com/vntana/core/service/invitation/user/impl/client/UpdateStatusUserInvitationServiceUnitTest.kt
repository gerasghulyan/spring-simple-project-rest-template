package com.vntana.core.service.invitation.user.impl.client

import com.vntana.core.domain.invitation.user.InvitationOrganizationClientUser
import com.vntana.core.service.invitation.user.AbstractUserInvitationToClientServiceUnitTest
import com.vntana.core.service.invitation.user.exception.InvitationUserNotFoundForUuidException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.easymock.EasyMock.isA
import org.junit.Test
import java.util.*

/**
 * Created by Diana Gevorgyan
 * Date: 11/27/20
 * Time: 9:27 AM
 */
class UpdateStatusUserInvitationServiceUnitTest : AbstractUserInvitationToClientServiceUnitTest() {

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
    fun `test update invitation organization not found`() {
        val dto = commonTestHelper.buildUpdateInvitationUserStatusDto()
        resetAll()
        expect(invitationOrganizationClientUserRepository.findByUuid(dto.uuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { invitationUserToClientService.updateStatus(dto) }.isExactlyInstanceOf(InvitationUserNotFoundForUuidException::class.java)
        verifyAll()
    }

    @Test
    fun `test update`() {
        val dto = commonTestHelper.buildUpdateInvitationUserStatusDto()
        val userInvitation = commonTestHelper.buildInvitationOrganizationClientUser()
        resetAll()
        expect(invitationOrganizationClientUserRepository.findByUuid(dto.uuid)).andReturn(Optional.of(userInvitation))
        expect(invitationOrganizationClientUserRepository.save(isA(InvitationOrganizationClientUser::class.java))).andReturn(userInvitation)
        replayAll()
        assertThat(invitationUserToClientService.updateStatus(dto)).isEqualTo(userInvitation)
        verifyAll()
    }
}
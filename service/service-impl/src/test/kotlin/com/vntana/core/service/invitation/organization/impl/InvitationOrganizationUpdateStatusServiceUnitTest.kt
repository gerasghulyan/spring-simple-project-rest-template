package com.vntana.core.service.invitation.organization.impl

import com.vntana.core.domain.invitation.organization.InvitationOrganization
import com.vntana.core.service.invitation.organization.AbstractInvitationOrganizationServiceUnitTest
import com.vntana.core.service.invitation.organization.exception.InvitationOrganizationNotFoundForUuidException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Manuk Gharslyan.
 * Date: 4/9/2020
 * Time: 10:41 AM
 */
class InvitationOrganizationUpdateStatusServiceUnitTest : AbstractInvitationOrganizationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildUpdateInvitationOrganizationStatusDto(uuid = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildUpdateInvitationOrganizationStatusDto(uuid = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildUpdateInvitationOrganizationStatusDto(status = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test update`() {
        val dto = commonTestHelper.buildUpdateInvitationOrganizationStatusDto()
        val invitationOrganization = commonTestHelper.buildInvitationOrganization()
        resetAll()
        expect(invitationOrganizationRepository.findByUuid(dto.uuid)).andReturn(Optional.of(invitationOrganization))
        expect(invitationOrganizationRepository.save(EasyMock.isA(InvitationOrganization::class.java))).andReturn(invitationOrganization)
        replayAll()
        assertThat(invitationOrganizationService.updateStatus(dto)).isEqualTo(invitationOrganization)
        verifyAll()
    }

    @Test
    fun `test update invitation organization not found`() {
        val dto = commonTestHelper.buildUpdateInvitationOrganizationStatusDto()
        resetAll()
        expect(invitationOrganizationRepository.findByUuid(dto.uuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { invitationOrganizationService.updateStatus(dto) }.isExactlyInstanceOf(InvitationOrganizationNotFoundForUuidException::class.java)
        verifyAll()
    }
}
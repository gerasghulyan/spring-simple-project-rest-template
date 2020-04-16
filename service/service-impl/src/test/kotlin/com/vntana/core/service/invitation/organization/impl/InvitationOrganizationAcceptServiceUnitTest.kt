package com.vntana.core.service.invitation.organization.impl

import com.vntana.core.domain.invitation.organization.InvitationOrganization
import com.vntana.core.domain.organization.Organization
import com.vntana.core.service.invitation.organization.AbstractInvitationOrganizationServiceUnitTest
import com.vntana.core.service.invitation.organization.exception.InvitationOrganizationNotFoundForUuidException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.easymock.EasyMock.isA
import org.junit.Test
import java.util.*

/**
 * Created by Manuk Gharslyan.
 * Date: 4/9/2020
 * Time: 10:41 AM
 */
class InvitationOrganizationAcceptServiceUnitTest : AbstractInvitationOrganizationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildUpdateOrganizationInvitationOrganizationDto(uuid = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildUpdateOrganizationInvitationOrganizationDto(uuid = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildUpdateOrganizationInvitationOrganizationDto(organizationUuid = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildUpdateOrganizationInvitationOrganizationDto(organizationUuid = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        val dto = commonTestHelper.buildUpdateOrganizationInvitationOrganizationDto()
        val organization = organizationCommonTestHelper.buildOrganization()
        val invitationOrganization = commonTestHelper.buildInvitationOrganization()
        resetAll()
        expect(invitationOrganizationRepository.findByUuid(dto.uuid)).andReturn(Optional.of(invitationOrganization))
        expect(organizationService.getByUuid(dto.organizationUuid)).andReturn(organization)
        expect(invitationOrganizationRepository.save(isA(InvitationOrganization::class.java))).andReturn(invitationOrganization).times(2)
        replayAll()
        assertThat(invitationOrganizationService.accept(dto)).isEqualTo(invitationOrganization)
        verifyAll()
    }
}
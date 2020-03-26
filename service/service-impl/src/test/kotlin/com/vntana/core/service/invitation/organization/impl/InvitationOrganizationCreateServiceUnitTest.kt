package com.vntana.core.service.invitation.organization.impl

import com.vntana.core.domain.invitation.organization.InvitationOrganization
import com.vntana.core.service.invitation.organization.AbstractInvitationOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.easymock.EasyMock.isA
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/25/20
 * Time: 4:08 PM
 */
class InvitationOrganizationCreateServiceUnitTest : AbstractInvitationOrganizationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildCreateInvitationOrganizationDto(ownerFullName = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationOrganizationDto(ownerFullName = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationOrganizationDto(email = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationOrganizationDto(email = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationOrganizationDto(organizationName = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationOrganizationDto(organizationName = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationOrganizationDto(slug = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationOrganizationDto(slug = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationOrganizationDto(customerSubscriptionDefinitionUuid = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationOrganizationDto(customerSubscriptionDefinitionUuid = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationOrganizationDto(status = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { invitationOrganizationService.create(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val dto = commonTestHelper.buildCreateInvitationOrganizationDto()
        val invitationOrganization = commonTestHelper.buildInvitationOrganization()
        expect(invitationOrganizationRepository.save(isA(InvitationOrganization::class.java))).andReturn(invitationOrganization)
        replayAll()
        assertThat(invitationOrganizationService.create(dto)).isEqualTo(invitationOrganization)
        verifyAll()
    }
}
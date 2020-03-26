package com.vntana.core.service.invitation.organization.impl

import com.vntana.core.service.invitation.organization.AbstractInvitationOrganizationServiceUnitTest
import com.vntana.core.service.invitation.organization.exception.InvitationOrganizationNotFoundForUuidException
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 3/25/20
 * Time: 4:10 PM
 */
class InvitationOrganizationGetByUuidServiceUnitTest : AbstractInvitationOrganizationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        Assertions.assertThatThrownBy { invitationOrganizationService.getByUuid(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { invitationOrganizationService.getByUuid(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val uuid = uuid()
        expect(invitationOrganizationRepository.findByUuid(uuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { invitationOrganizationService.getByUuid(uuid) }
                .isExactlyInstanceOf(InvitationOrganizationNotFoundForUuidException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val invitationOrganization = commonTestHelper.buildInvitationOrganization()
        val uuid = invitationOrganization.uuid
        expect(invitationOrganizationRepository.findByUuid(uuid)).andReturn(Optional.of(invitationOrganization))
        replayAll()
        assertThat(invitationOrganizationService.getByUuid(uuid)).isEqualTo(invitationOrganization)
        verifyAll()
    }
}
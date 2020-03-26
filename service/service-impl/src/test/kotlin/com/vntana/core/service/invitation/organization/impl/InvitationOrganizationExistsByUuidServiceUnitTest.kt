package com.vntana.core.service.invitation.organization.impl

import com.vntana.core.service.invitation.organization.AbstractInvitationOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/25/20
 * Time: 4:10 PM
 */
class InvitationOrganizationExistsByUuidServiceUnitTest : AbstractInvitationOrganizationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { invitationOrganizationService.existsByUuid(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { invitationOrganizationService.existsByUuid(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when does not exist`() {
        resetAll()
        val uuid = uuid()
        expect(invitationOrganizationRepository.existsByUuid(uuid)).andReturn(false)
        replayAll()
        assertThat(invitationOrganizationService.existsByUuid(uuid)).isFalse()
        verifyAll()
    }

    @Test
    fun `test when found`() {
        resetAll()
        val uuid = uuid()
        expect(invitationOrganizationRepository.existsByUuid(uuid)).andReturn(true)
        replayAll()
        assertThat(invitationOrganizationService.existsByUuid(uuid)).isTrue()
        verifyAll()
    }
}
package com.vntana.core.service.invitation.organization.impl

import com.vntana.core.service.invitation.organization.AbstractInvitationOrganizationServiceUnitTest
import com.vntana.core.service.invitation.organization.dto.GetAllInvitationOrganizationsDto
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import org.springframework.data.domain.PageRequest

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 4:44 PM
 */
class InvitationOrganizationGetAllServiceUnitTest : AbstractInvitationOrganizationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { invitationOrganizationService.getAll(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val page = 10
        val size = 15
        val dto = GetAllInvitationOrganizationsDto(page, size)
        val pageOfInvitations = commonTestHelper.buildInvitationOrganizationPage()
        expect(invitationOrganizationRepository.findAll(PageRequest.of(page, size))).andReturn(pageOfInvitations)
        replayAll()
        assertThat(invitationOrganizationService.getAll(dto)).isEqualTo(pageOfInvitations)
        verifyAll()
    }
}
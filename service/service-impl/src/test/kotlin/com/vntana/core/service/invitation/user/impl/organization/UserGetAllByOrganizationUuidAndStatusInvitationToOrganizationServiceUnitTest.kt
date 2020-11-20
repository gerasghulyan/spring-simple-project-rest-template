package com.vntana.core.service.invitation.user.impl.organization

import com.vntana.core.service.invitation.user.AbstractUserInvitationToOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import org.springframework.data.domain.PageRequest

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:43 PM
 */
class UserGetAllByOrganizationUuidAndStatusInvitationToOrganizationServiceUnitTest : AbstractUserInvitationToOrganizationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { invitationUserToOrganizationService.getAllByOrganizationUuidAndStatus(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val page = 0
        val size = 2
        val dto = commonTestHelper.buildGetAllByOrganizationUuidAndStatusInvitationUsersDto(page, size)
        val pageOfInvitations = commonTestHelper.buildInvitationUserPage()
        expect(invitationOrganizationUserRepository.findAllByOrganizationUuidAndStatus(dto.organizationUuid, dto.status, PageRequest.of(page, size))).andReturn(pageOfInvitations)
        replayAll()
        assertThat(invitationUserToOrganizationService.getAllByOrganizationUuidAndStatus(dto)).isEqualTo(pageOfInvitations)
        verifyAll()
    }
}
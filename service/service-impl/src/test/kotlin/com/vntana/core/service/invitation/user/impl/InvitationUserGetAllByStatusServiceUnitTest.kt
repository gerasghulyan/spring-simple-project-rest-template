package com.vntana.core.service.invitation.user.impl

import com.vntana.core.service.invitation.user.AbstractInvitationUserServiceUnitTest
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
class InvitationUserGetAllByStatusServiceUnitTest : AbstractInvitationUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { invitationUserService.getAllByStatus(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val page = 0
        val size = 2
        val dto = commonTestHelper.buildGetAllByStatusInvitationUsersDto(page, size)
        val pageOfInvitations = commonTestHelper.buildInvitationUserPage()
        expect(invitationUserRepository.findAllByStatus(dto.status, PageRequest.of(page, size))).andReturn(pageOfInvitations)
        replayAll()
        assertThat(invitationUserService.getAllByStatus(dto)).isEqualTo(pageOfInvitations)
        verifyAll()
    }
}
package com.vntana.core.service.invitation.user.impl

import com.vntana.core.service.invitation.user.AbstractInvitationUserServiceUnitTest
import com.vntana.core.service.invitation.user.dto.GetAllInvitationUsersDto
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
class InvitationUserGetAllServiceUnitTest : AbstractInvitationUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { invitationUserService.getAll(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val page = 10
        val size = 15
        val dto = GetAllInvitationUsersDto(page, size)
        val pageOfInvitations = commonTestHelper.buildInvitationUserPage()
        expect(invitationUserRepository.findAll(PageRequest.of(page, size))).andReturn(pageOfInvitations)
        replayAll()
        assertThat(invitationUserService.getAll(dto)).isEqualTo(pageOfInvitations)
        verifyAll()
    }
}
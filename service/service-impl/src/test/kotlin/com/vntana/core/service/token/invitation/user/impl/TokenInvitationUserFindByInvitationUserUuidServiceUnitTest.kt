package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Manuk Gharslyan.
 * Date: 5/18/2020
 * Time: 10:04 AM
 */
class TokenInvitationUserFindByInvitationUserUuidServiceUnitTest : AbstractTokenInvitationUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        assertThatThrownBy { tokenInvitationUserService.findByInvitationUserUuid(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenInvitationUserService.findByInvitationUserUuid(emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        replayAll()
        verifyAll()
    }

    @Test
    fun `test not found`() {
        val invitationUserUuid = uuid()
        resetAll()
        expect(tokenRepository.findByInvitationUserUuid(invitationUserUuid)).andReturn(Optional.empty())
        replayAll()
        assertThat(tokenInvitationUserService.findByInvitationUserUuid(invitationUserUuid)).isEmpty
        verifyAll()
    }

    @Test
    fun test() {
        val invitationUser = invitationUserCommonTestHelper.buildInvitationUser()
        val tokenInvitationUser = commonTestHelper.buildTokenInvitationUser(invitationOrganizationUser = invitationUser)
        resetAll()
        expect(tokenRepository.findByInvitationUserUuid(invitationUser.uuid)).andReturn(Optional.of(tokenInvitationUser))
        replayAll()
        val response = tokenInvitationUserService.findByInvitationUserUuid(invitationUser.uuid)
        assertThat(response).isNotEmpty
        assertThat(response.get()).isEqualTo(tokenInvitationUser)
        verifyAll()
    }
}
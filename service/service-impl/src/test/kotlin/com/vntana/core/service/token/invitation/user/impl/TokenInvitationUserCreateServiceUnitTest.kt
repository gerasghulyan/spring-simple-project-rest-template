package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.domain.token.TokenInvitationUser
import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 12:29 PM
 */
class TokenInvitationUserCreateServiceUnitTest : AbstractTokenInvitationUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildCreateTokenInvitationUserDto(token = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateTokenInvitationUserDto(token = emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateTokenInvitationUserDto(invitationUserUuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateTokenInvitationUserDto(invitationUserUuid = emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenInvitationUserService.create(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val invitationUser = invitationUserCommonTestHelper.buildInvitationUser()
        val dto = commonTestHelper.buildCreateTokenInvitationUserDto(
                invitationUserUuid = invitationUser.uuid
        )
        expect(invitationUserService.getByUuid(dto.invitationUserUuid)).andReturn(invitationUser)
        expect(tokenRepository.save(isA(TokenInvitationUser::class.java))).andAnswer { getCurrentArguments()[0] as TokenInvitationUser }
        replayAll()
        tokenInvitationUserService.create(dto).let {
            assertThat(it.token).isEqualTo(dto.token)
            assertThat(it.invitationUser.uuid).isEqualTo(dto.invitationUserUuid)
        }
        verifyAll()
    }
}
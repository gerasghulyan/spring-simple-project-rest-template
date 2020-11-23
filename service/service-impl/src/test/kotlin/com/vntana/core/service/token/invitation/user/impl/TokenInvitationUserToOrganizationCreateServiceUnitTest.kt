package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.domain.token.TokenUserInvitationToOrganization
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
class TokenInvitationUserToOrganizationCreateServiceUnitTest : AbstractTokenInvitationUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildCreateTokenInvitationUserToOrganizationDto(token = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateTokenInvitationUserToOrganizationDto(token = emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateTokenInvitationUserToOrganizationDto(invitationUserUuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateTokenInvitationUserToOrganizationDto(invitationUserUuid = emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenInvitationUserService.createUserInvitationToOrganization(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val invitationUser = invitationUserCommonTestHelper.buildInvitationUserToOrganization()
        val dto = commonTestHelper.buildCreateTokenInvitationUserToOrganizationDto(
                invitationUserUuid = invitationUser.uuid
        )
        expect(invitationUserToOrganizationService.getByUuid(dto.invitationUserUuid)).andReturn(invitationUser)
        expect(tokenInvitationToOrganizationRepository.save(isA(TokenUserInvitationToOrganization::class.java))).andAnswer { getCurrentArguments()[0] as TokenUserInvitationToOrganization }
        replayAll()
        tokenInvitationUserService.createUserInvitationToOrganization(dto).let {
            assertThat(it.token).isEqualTo(dto.token)
            assertThat(it.invitationUser.uuid).isEqualTo(dto.invitationUserUuid)
        }
        verifyAll()
    }
}
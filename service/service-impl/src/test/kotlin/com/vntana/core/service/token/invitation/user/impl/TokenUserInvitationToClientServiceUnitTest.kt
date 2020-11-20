package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.domain.token.TokenUserInvitationToOrganizationClient
import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceUnitTest
import com.vntana.core.service.token.invitation.user.dto.CreateInvitationUserToClientDto
import org.assertj.core.api.Assertions
import org.easymock.EasyMock.anyObject
import org.easymock.EasyMock.expect
import org.junit.Test


/**
 * Created by Diana Gevorgyan
 * Date: 11/20/20
 * Time: 12:15 PM
 */
class TokenUserInvitationToClientServiceUnitTest : AbstractTokenInvitationUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        Assertions.assertThatThrownBy { commonTestHelper.buildInvitationUuidAndTokenDto(token = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { commonTestHelper.buildInvitationUuidAndTokenDto(userInvitationUuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `test with valid arguments`() {
        val invitationUser1 = invitationUserCommonTestHelper.buildInvitationOrganizationClientUser()
        val invitationUser2 = invitationUserCommonTestHelper.buildInvitationOrganizationClientUser()
        val invitationUuidAndToken1 = commonTestHelper.buildInvitationUuidAndTokenDto()
        val invitationUuidAndToken2 = commonTestHelper.buildInvitationUuidAndTokenDto()
        val dto = CreateInvitationUserToClientDto(listOf(invitationUuidAndToken1, invitationUuidAndToken2))
        val token1 = TokenUserInvitationToOrganizationClient(invitationUuidAndToken1.token, invitationUser1)
        val token2 = TokenUserInvitationToOrganizationClient(invitationUuidAndToken2.token, invitationUser2)
        resetAll()
        expect(invitationUserToClientService.getByUuid(dto.invitationUuidAndTokens[0].invitationUuid)).andReturn(invitationUser1)
        expect(invitationUserToClientService.getByUuid(dto.invitationUuidAndTokens[1].invitationUuid)).andReturn(invitationUser2)
        expect(tokenUserInvitationToOrganizationClientRepository
                .saveAll(anyObject<List<TokenUserInvitationToOrganizationClient>>()))
                .andAnswer { listOf(token1, token2) }
        replayAll()
        tokenInvitationUserService.createUserInvitationToClients(dto).let {
            Assertions.assertThat(it.size).isEqualTo(dto.invitationUuidAndTokens.size)
            Assertions.assertThat(it).containsExactlyElementsOf(listOf(token1, token2))
        }
        verifyAll()
    }
}
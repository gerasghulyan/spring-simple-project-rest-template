package com.vntana.core.rest.facade.token.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.domain.invitation.user.InvitationOrganizationClientUser
import com.vntana.core.domain.token.TokenUserInvitationToOrganizationClient
import com.vntana.core.model.token.error.TokenErrorResponseModel
import com.vntana.core.rest.facade.token.AbstractTokenFacadeUnitTest
import com.vntana.core.service.token.invitation.user.dto.CreateInvitationUserToClientDto
import com.vntana.core.service.token.invitation.user.dto.InvitationUuidAndTokenDto
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 11/20/20
 * Time: 1:33 PM
 */
class TokenCreateUserInvitationToClientUnitTest : AbstractTokenFacadeUnitTest() {

    @Test
    fun `test when precondition failed`() {
        val request = restTestHelper.buildCreateTokenUserInvitationToClientRequest(1)
        resetAll()
        expect(preconditionChecker.checkCreateTokenUserInvitationToClient(request))
                .andReturn(SingleErrorWithStatus.of(400, TokenErrorResponseModel.BAD_REQUEST))
        replayAll()
        assertBasicErrorResultResponse(tokenServiceFacade.createTokenInvitationUserToClient(request), TokenErrorResponseModel.BAD_REQUEST)
        verifyAll()
    }

    @Test
    fun test() {
        val userInvitation = InvitationOrganizationClientUser()
        val request = restTestHelper.buildCreateTokenUserInvitationToClientRequest(1)
        val invitationAndTokenRequestModel = request.tokens[0]
        val dto = CreateInvitationUserToClientDto(
                listOf(InvitationUuidAndTokenDto(invitationAndTokenRequestModel.userInvitationUuid, invitationAndTokenRequestModel.token)))
        val savedTokens = listOf(TokenUserInvitationToOrganizationClient(invitationAndTokenRequestModel.token, userInvitation))
        resetAll()
        expect(preconditionChecker.checkCreateTokenUserInvitationToClient(request)).andReturn(SingleErrorWithStatus.empty())
        expect(mapperFacade.map(request, CreateInvitationUserToClientDto::class.java)).andReturn(dto)
        expect(tokenInvitationUserService.createUserInvitationToClients(dto)).andReturn(savedTokens)
        replayAll()
        assertBasicSuccessResultResponse(tokenServiceFacade.createTokenInvitationUserToClient(request))
        verifyAll()
    }
}
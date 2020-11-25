package com.vntana.core.rest.facade.token.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.token.error.TokenErrorResponseModel
import com.vntana.core.rest.facade.token.AbstractTokenFacadeUnitTest
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 3:16 PM
 */
class TokenCreateTokenInvitationUserToOrganizaionFacadeUnitTest : AbstractTokenFacadeUnitTest() {

    @Test
    fun `test when precondition failed`() {
        resetAll()
        val request = restTestHelper.buildCreateTokenInvitationUserRequest()
        expect(preconditionChecker.checkCreateTokenUserInvitationToOrganization(request))
                .andReturn(SingleErrorWithStatus.of(404, TokenErrorResponseModel.TOKEN_NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(tokenServiceFacade.createTokenInvitationUserToOrganization(request), TokenErrorResponseModel.TOKEN_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildCreateTokenInvitationUserRequest()
        val dto = commonTestHelper.buildCreateTokenInvitationUserToOrganizationDto(
                token = request.token,
                invitationUserUuid = request.userInvitationUuid
        )
        val tokenInvitationUser = commonTestHelper.buildTokenInvitationUserToOrganization()
        expect(preconditionChecker.checkCreateTokenUserInvitationToOrganization(request)).andReturn(SingleErrorWithStatus.empty())
        expect(tokenInvitationUserService.createUserInvitationToOrganization(dto)).andReturn(tokenInvitationUser)
        replayAll()
        assertBasicSuccessResultResponse(tokenServiceFacade.createTokenInvitationUserToOrganization(request))
        verifyAll()
    }
}
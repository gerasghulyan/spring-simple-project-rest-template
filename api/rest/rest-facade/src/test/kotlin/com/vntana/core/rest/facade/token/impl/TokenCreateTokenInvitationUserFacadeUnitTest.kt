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
class TokenCreateTokenInvitationUserFacadeUnitTest : AbstractTokenFacadeUnitTest() {

    @Test
    fun `test when precondition failed`() {
        resetAll()
        val request = restTestHelper.buildCreateTokenInvitationUserRequest()
        expect(preconditionChecker.checkCreateTokenInvitationUser(request))
                .andReturn(SingleErrorWithStatus.of(404, TokenErrorResponseModel.TOKEN_NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(tokenServiceFacade.createTokenInvitationUser(request), TokenErrorResponseModel.TOKEN_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildCreateTokenInvitationUserRequest()
        val dto = commonTestHelper.buildCreateTokenInvitationUserDto(
                token = request.token,
                invitationUserUuid = request.invitationUserUuid
        )
        val tokenInvitationUser = commonTestHelper.buildTokenInvitationUser()
        expect(preconditionChecker.checkCreateTokenInvitationUser(request)).andReturn(SingleErrorWithStatus.empty())
        expect(tokenInvitationUserService.create(dto)).andReturn(tokenInvitationUser)
        replayAll()
        assertBasicSuccessResultResponse(tokenServiceFacade.createTokenInvitationUser(request))
        verifyAll()
    }
}
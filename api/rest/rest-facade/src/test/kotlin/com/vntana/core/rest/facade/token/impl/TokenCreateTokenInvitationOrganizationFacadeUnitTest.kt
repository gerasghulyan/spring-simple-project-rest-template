package com.vntana.core.rest.facade.token.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.token.error.TokenErrorResponseModel
import com.vntana.core.rest.facade.token.AbstractTokenFacadeUnitTest
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 3:12 PM
 */
class TokenCreateTokenInvitationOrganizationFacadeUnitTest : AbstractTokenFacadeUnitTest() {

    @Test
    fun `test when precondition failed`() {
        resetAll()
        val request = restTestHelper.buildCreateTokenInvitationOrganizationRequest()
        expect(preconditionChecker.checkCreateTokenInvitationOrganization(request))
                .andReturn(SingleErrorWithStatus.of(404, TokenErrorResponseModel.TOKEN_NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(tokenServiceFacade.createTokenInvitationOrganization(request), TokenErrorResponseModel.TOKEN_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildCreateTokenInvitationOrganizationRequest()
        val dto = commonTestHelper.buildCreateTokenInvitationOrganizationDto(
                token = request.token,
                invitationOrganizationUuid = request.invitationOrganizationUuid
        )
        val tokenInvitationOrganization = commonTestHelper.buildTokenInvitationOrganization()
        expect(preconditionChecker.checkCreateTokenInvitationOrganization(request)).andReturn(SingleErrorWithStatus.empty())
        expect(tokenInvitationOrganizationService.create(dto)).andReturn(tokenInvitationOrganization)
        replayAll()
        assertBasicSuccessResultResponse(tokenServiceFacade.createTokenInvitationOrganization(request))
        verifyAll()
    }
}
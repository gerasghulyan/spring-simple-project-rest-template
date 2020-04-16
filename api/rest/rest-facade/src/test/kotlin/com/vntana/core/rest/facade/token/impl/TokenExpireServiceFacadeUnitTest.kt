package com.vntana.core.rest.facade.token.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.token.error.TokenErrorResponseModel
import com.vntana.core.rest.facade.token.AbstractTokenFacadeUnitTest
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Manuk Gharslyan.
 * Date: 4/8/2020
 * Time: 11:19 AM
 */
class TokenExpireServiceFacadeUnitTest : AbstractTokenFacadeUnitTest() {

    @Test
    fun `test with precondition errors`() {
        val token = uuid()
        resetAll()
        expect(preconditionChecker.checkExpire(token)).andReturn(SingleErrorWithStatus.of(statusCode(), TokenErrorResponseModel.TOKEN_NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(tokenServiceFacade.expire(token), TokenErrorResponseModel.TOKEN_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun `test expire`() {
        val token = uuid()
        val tokenInvitationOrganization = commonTestHelper.buildTokenInvitationOrganization()
        resetAll()
        expect(preconditionChecker.checkExpire(token)).andReturn(SingleErrorWithStatus.empty())
        expect(tokenService.findByToken(token)).andReturn(Optional.of(tokenInvitationOrganization))
        expect(tokenService.expire(tokenInvitationOrganization.uuid)).andReturn(tokenInvitationOrganization)
        replayAll()
        assertBasicSuccessResultResponse(tokenServiceFacade.expire(token))
        verifyAll()
    }
}
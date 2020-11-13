package com.vntana.core.rest.facade.token.auth.impl

import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel
import com.vntana.core.rest.facade.token.auth.AbstractTokenAuthenticationServiceFacadeUnitTest
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 2:36 PM
 */
class TokenAuthenticationExpireFacadeUnitTest : AbstractTokenAuthenticationServiceFacadeUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertBasicErrorResultResponse(tokenAuthenticationServiceFacade.expire(null), TokenAuthenticationErrorResponseModel.MISSING_TOKEN)
        assertBasicErrorResultResponse(tokenAuthenticationServiceFacade.expire(""), TokenAuthenticationErrorResponseModel.MISSING_TOKEN)
        verifyAll()
    }

    @Test
    fun `test when token not found`() {
        resetAll()
        val token = uuid()
        expect(tokenAuthenticationService.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(tokenAuthenticationServiceFacade.expire(token), TokenAuthenticationErrorResponseModel.TOKEN_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val authToken = commonTestHelper.buildTokenAuthentication()
        val token = authToken.token
        expect(tokenAuthenticationService.findByToken(token)).andReturn(Optional.of(authToken))
        expect(tokenAuthenticationService.expire(authToken.uuid)).andReturn(authToken)
        replayAll()
        assertBasicSuccessResultResponse(tokenAuthenticationServiceFacade.expire(token))
        verifyAll()
    }
}
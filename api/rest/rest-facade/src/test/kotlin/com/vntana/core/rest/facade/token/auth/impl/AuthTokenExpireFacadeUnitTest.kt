package com.vntana.core.rest.facade.token.auth.impl

import com.vntana.core.model.token.auth.error.AuthTokenErrorResponseModel
import com.vntana.core.rest.facade.token.auth.AbstractAuthTokenServiceFacadeUnitTest
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 2:36 PM
 */
class AuthTokenExpireFacadeUnitTest : AbstractAuthTokenServiceFacadeUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertBasicErrorResultResponse(authTokenServiceFacade.expire(null), AuthTokenErrorResponseModel.MISSING_TOKEN)
        assertBasicErrorResultResponse(authTokenServiceFacade.expire(""), AuthTokenErrorResponseModel.MISSING_TOKEN)
        verifyAll()
    }

    @Test
    fun `test when token not found`() {
        resetAll()
        val token = uuid()
        expect(authTokenService.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(authTokenServiceFacade.expire(token), AuthTokenErrorResponseModel.TOKEN_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val authToken = commonTestHelper.buildAuthToken()
        val token = authToken.token
        expect(authTokenService.findByToken(token)).andReturn(Optional.of(authToken))
        expect(authTokenService.expire(authToken.uuid)).andReturn(authToken)
        replayAll()
        assertBasicSuccessResultResponse(authTokenServiceFacade.expire(token))
        verifyAll()
    }
}
package com.vntana.core.rest.facade.token.auth.impl

import com.vntana.core.model.token.auth.error.AuthTokenErrorResponseModel
import com.vntana.core.rest.facade.token.auth.AbstractAuthTokenServiceFacadeUnitTest
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan
 * Date: 7/15/20
 * Time: 3:27 PM
 */
class AuthTokenFindByTokenFacadeUnitTest : AbstractAuthTokenServiceFacadeUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertBasicErrorResultResponse(authTokenServiceFacade.findByToken(null), AuthTokenErrorResponseModel.MISSING_TOKEN)
        assertBasicErrorResultResponse(authTokenServiceFacade.findByToken(""), AuthTokenErrorResponseModel.MISSING_TOKEN)
        verifyAll()
    }

    @Test
    fun `test when token not found`() {
        resetAll()
        val token = uuid()
        expect(authTokenService.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(authTokenServiceFacade.findByToken(token), AuthTokenErrorResponseModel.TOKEN_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val authToken = commonTestHelper.buildAuthToken()
        val token = authToken.token
        expect(authTokenService.findByToken(token)).andReturn(Optional.of(authToken))
        replayAll()
        assertBasicSuccessResultResponse(authTokenServiceFacade.findByToken(token))
        verifyAll()
    }
}
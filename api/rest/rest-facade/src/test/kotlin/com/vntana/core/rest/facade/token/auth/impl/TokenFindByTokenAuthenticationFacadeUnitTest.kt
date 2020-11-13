package com.vntana.core.rest.facade.token.auth.impl

import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel
import com.vntana.core.rest.facade.token.auth.AbstractTokenAuthenticationServiceFacadeUnitTest
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan
 * Date: 7/15/20
 * Time: 3:27 PM
 */
class TokenFindByTokenAuthenticationFacadeUnitTest : AbstractTokenAuthenticationServiceFacadeUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertBasicErrorResultResponse(tokenAuthenticationServiceFacade.findByToken(null), TokenAuthenticationErrorResponseModel.MISSING_TOKEN)
        assertBasicErrorResultResponse(tokenAuthenticationServiceFacade.findByToken(""), TokenAuthenticationErrorResponseModel.MISSING_TOKEN)
        verifyAll()
    }

    @Test
    fun `test when token not found`() {
        resetAll()
        val token = uuid()
        expect(tokenAuthenticationService.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(tokenAuthenticationServiceFacade.findByToken(token), TokenAuthenticationErrorResponseModel.TOKEN_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val authToken = commonTestHelper.buildTokenAuthentication()
        val token = authToken.token
        expect(tokenAuthenticationService.findByToken(token)).andReturn(Optional.of(authToken))
        replayAll()
        assertBasicSuccessResultResponse(tokenAuthenticationServiceFacade.findByToken(token))
        verifyAll()
    }
}
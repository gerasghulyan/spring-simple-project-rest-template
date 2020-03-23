package com.vntana.core.rest.facade.token.auth.impl

import com.vntana.core.model.token.auth.error.AuthTokenErrorResponseModel
import com.vntana.core.rest.facade.token.auth.AbstractAuthTokenServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 2:29 PM
 */
class AuthTokenIsExpiredFacadeUnitTest : AbstractAuthTokenServiceFacadeUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertBasicErrorResultResponse(authTokenServiceFacade.isExpired(null), AuthTokenErrorResponseModel.MISSING_TOKEN)
        assertBasicErrorResultResponse(authTokenServiceFacade.isExpired(""), AuthTokenErrorResponseModel.MISSING_TOKEN)
        verifyAll()
    }

    @Test
    fun `test when token not found`() {
        resetAll()
        val token = uuid()
        expect(authTokenService.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(authTokenServiceFacade.isExpired(token), AuthTokenErrorResponseModel.TOKEN_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun `test when not expired`() {
        resetAll()
        val authToken = commonTestHelper.buildAuthToken()
        val token = authToken.token
        expect(authTokenService.findByToken(token)).andReturn(Optional.of(authToken))
        replayAll()
        authTokenServiceFacade.isExpired(token).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().expired).isFalse()
        }
        verifyAll()
    }

    @Test
    fun `test when expired`() {
        resetAll()
        val authToken = commonTestHelper.buildAuthToken()
        authToken.expire()
        val token = authToken.token
        expect(authTokenService.findByToken(token)).andReturn(Optional.of(authToken))
        replayAll()
        authTokenServiceFacade.isExpired(token).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().expired).isTrue()
        }
        verifyAll()
    }
}
package com.vntana.core.rest.facade.token.auth.impl

import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel
import com.vntana.core.rest.facade.token.auth.AbstractTokenAuthenticationServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 2:29 PM
 */
class TokenAuthenticationIsExpiredFacadeUnitTest : AbstractTokenAuthenticationServiceFacadeUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertBasicErrorResultResponse(tokenAuthenticationServiceFacade.isExpired(null), TokenAuthenticationErrorResponseModel.MISSING_TOKEN)
        assertBasicErrorResultResponse(tokenAuthenticationServiceFacade.isExpired(""), TokenAuthenticationErrorResponseModel.MISSING_TOKEN)
        verifyAll()
    }

    @Test
    fun `test when token not found`() {
        resetAll()
        val token = uuid()
        expect(tokenAuthenticationService.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(tokenAuthenticationServiceFacade.isExpired(token), TokenAuthenticationErrorResponseModel.TOKEN_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun `test when not expired`() {
        resetAll()
        val authToken = commonTestHelper.buildTokenAuthentication()
        val token = authToken.token
        expect(tokenAuthenticationService.findByToken(token)).andReturn(Optional.of(authToken))
        replayAll()
        tokenAuthenticationServiceFacade.isExpired(token).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().expired).isFalse()
        }
        verifyAll()
    }

    @Test
    fun `test when expired`() {
        resetAll()
        val authToken = commonTestHelper.buildTokenAuthentication()
        authToken.expire()
        val token = authToken.token
        expect(tokenAuthenticationService.findByToken(token)).andReturn(Optional.of(authToken))
        replayAll()
        tokenAuthenticationServiceFacade.isExpired(token).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().expired).isTrue()
        }
        verifyAll()
    }
}
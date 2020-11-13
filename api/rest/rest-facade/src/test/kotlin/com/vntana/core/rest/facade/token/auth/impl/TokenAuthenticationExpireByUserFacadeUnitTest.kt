package com.vntana.core.rest.facade.token.auth.impl

import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel
import com.vntana.core.rest.facade.token.auth.AbstractTokenAuthenticationServiceFacadeUnitTest
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 2:36 PM
 */
class TokenAuthenticationExpireByUserFacadeUnitTest : AbstractTokenAuthenticationServiceFacadeUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertBasicErrorResultResponse(tokenAuthenticationServiceFacade.expireByUser(null), TokenAuthenticationErrorResponseModel.MISSING_USER_UUID)
        assertBasicErrorResultResponse(tokenAuthenticationServiceFacade.expireByUser(""), TokenAuthenticationErrorResponseModel.MISSING_USER_UUID)
        verifyAll()
    }

    @Test
    fun `test when token not found`() {
        resetAll()
        val userUuid = uuid()
        expect(userService.existsByUuid(userUuid)).andReturn(false)
        replayAll()
        assertBasicErrorResultResponse(tokenAuthenticationServiceFacade.expireByUser(userUuid), TokenAuthenticationErrorResponseModel.USER_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val userUuid = uuid()
        expect(userService.existsByUuid(userUuid)).andReturn(true)
        expect(tokenAuthenticationService.expireAllByUser(userUuid))
        replayAll()
        assertBasicSuccessResultResponse(tokenAuthenticationServiceFacade.expireByUser(userUuid))
        verifyAll()
    }
}
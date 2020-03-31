package com.vntana.core.rest.facade.token.auth.impl

import com.vntana.core.model.token.auth.error.AuthTokenErrorResponseModel
import com.vntana.core.rest.facade.token.auth.AbstractAuthTokenServiceFacadeUnitTest
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 2:36 PM
 */
class AuthTokenExpireByUserFacadeUnitTest : AbstractAuthTokenServiceFacadeUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertBasicErrorResultResponse(authTokenServiceFacade.expireByUser(null), AuthTokenErrorResponseModel.MISSING_USER_UUID)
        assertBasicErrorResultResponse(authTokenServiceFacade.expireByUser(""), AuthTokenErrorResponseModel.MISSING_USER_UUID)
        verifyAll()
    }

    @Test
    fun `test when token not found`() {
        resetAll()
        val userUuid = uuid()
        expect(userService.existsByUuid(userUuid)).andReturn(false)
        replayAll()
        assertBasicErrorResultResponse(authTokenServiceFacade.expireByUser(userUuid), AuthTokenErrorResponseModel.USER_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val userUuid = uuid()
        expect(userService.existsByUuid(userUuid)).andReturn(true)
        expect(authTokenService.expireAllByUser(userUuid))
        replayAll()
        assertBasicSuccessResultResponse(authTokenServiceFacade.expireByUser(userUuid))
        verifyAll()
    }
}
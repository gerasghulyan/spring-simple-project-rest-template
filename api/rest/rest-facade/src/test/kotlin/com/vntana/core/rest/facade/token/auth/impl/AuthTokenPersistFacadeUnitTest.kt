package com.vntana.core.rest.facade.token.auth.impl

import com.vntana.core.model.token.auth.error.AuthTokenErrorResponseModel
import com.vntana.core.rest.facade.token.auth.AbstractAuthTokenServiceFacadeUnitTest
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 2:21 PM
 */
class AuthTokenPersistFacadeUnitTest : AbstractAuthTokenServiceFacadeUnitTest() {

    @Test
    fun `test when user not found`() {
        resetAll()
        val request = restTestHelper.buildAuthTokenPersistRequest()
        expect(userService.existsByUuid(request.userUuid)).andReturn(false)
        replayAll()
        assertBasicErrorResultResponse(authTokenServiceFacade.persist(request), AuthTokenErrorResponseModel.USER_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildAuthTokenPersistRequest()
        val createDto = commonTestHelper.buildAuthTokenCreateDto(userUuid = request.userUuid, token = request.token, expiration = request.expiration)
        val authToken = commonTestHelper.buildAuthToken()
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(authTokenService.create(createDto)).andReturn(authToken)
        replayAll()
        assertBasicSuccessResultResponse(authTokenServiceFacade.persist(request))
        verifyAll()
    }
}
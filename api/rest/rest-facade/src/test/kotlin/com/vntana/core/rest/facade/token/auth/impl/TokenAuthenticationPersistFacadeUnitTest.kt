package com.vntana.core.rest.facade.token.auth.impl

import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel
import com.vntana.core.rest.facade.token.auth.AbstractTokenAuthenticationServiceFacadeUnitTest
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 2:21 PM
 */
class TokenAuthenticationPersistFacadeUnitTest : AbstractTokenAuthenticationServiceFacadeUnitTest() {

    @Test
    fun `test when user not found`() {
        resetAll()
        val request = restTestHelper.buildTokenAuthenticationPersistRequest()
        expect(userService.existsByUuid(request.userUuid)).andReturn(false)
        replayAll()
        assertBasicErrorResultResponse(tokenAuthenticationServiceFacade.persist(request), TokenAuthenticationErrorResponseModel.USER_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildTokenAuthenticationPersistRequest()
        val createDto = commonTestHelper.buildTokenAuthenticationCreateDto(userUuid = request.userUuid, token = request.token, expiration = request.expiration)
        val authToken = commonTestHelper.buildTokenAuthentication()
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(tokenAuthenticationService.create(createDto)).andReturn(authToken)
        replayAll()
        assertBasicSuccessResultResponse(tokenAuthenticationServiceFacade.persist(request))
        verifyAll()
    }
}
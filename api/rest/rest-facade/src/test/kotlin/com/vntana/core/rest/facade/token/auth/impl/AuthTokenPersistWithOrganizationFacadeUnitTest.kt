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
class AuthTokenPersistWithOrganizationFacadeUnitTest : AbstractAuthTokenServiceFacadeUnitTest() {

    @Test
    fun `test when user not found`() {
        resetAll()
        val request = restTestHelper.buildAuthTokenPersistWithOrganizationRequest()
        expect(userService.existsByUuid(request.userUuid)).andReturn(false)
        replayAll()
        assertBasicErrorResultResponse(authTokenServiceFacade.persistWithOrganization(request), AuthTokenErrorResponseModel.USER_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildAuthTokenPersistWithOrganizationRequest()
        val createDto = commonTestHelper.buildAuthTokenCreateWithOrganizationDto(
                userUuid = request.userUuid,
                token = request.token,
                organizationUuid = request.organizationUuid,
                expiration = request.expiration
        )
        val authToken = commonTestHelper.buildAuthTokenWithOrganization()
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(authTokenService.createWithOrganization(createDto)).andReturn(authToken)
        replayAll()
        assertBasicSuccessResultResponse(authTokenServiceFacade.persistWithOrganization(request))
        verifyAll()
    }
}
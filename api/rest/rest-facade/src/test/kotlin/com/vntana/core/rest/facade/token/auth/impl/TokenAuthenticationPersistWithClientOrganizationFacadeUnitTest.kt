package com.vntana.core.rest.facade.token.auth.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel
import com.vntana.core.rest.facade.token.auth.AbstractTokenAuthenticationServiceFacadeUnitTest
import org.apache.http.HttpStatus
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 2:21 PM
 */
class TokenAuthenticationPersistWithClientOrganizationFacadeUnitTest : AbstractTokenAuthenticationServiceFacadeUnitTest() {

    @Test
    fun `test when user not found`() {
        resetAll()
        val request = restTestHelper.buildTokenAuthenticationPersistWithClientOrganizationRequest()
        expect(preconditionCheckerComponent.checkPersistWithClientOrganization(request)).andReturn(SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, TokenAuthenticationErrorResponseModel.USER_NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(tokenAuthenticationServiceFacade.persistWithClientOrganization(request), TokenAuthenticationErrorResponseModel.USER_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun `test when client organization not found`() {
        resetAll()
        val request = restTestHelper.buildTokenAuthenticationPersistWithClientOrganizationRequest()
        expect(preconditionCheckerComponent.checkPersistWithClientOrganization(request)).andReturn(SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, TokenAuthenticationErrorResponseModel.ORGANIZATION_NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(tokenAuthenticationServiceFacade.persistWithClientOrganization(request), TokenAuthenticationErrorResponseModel.ORGANIZATION_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildTokenAuthenticationPersistWithClientOrganizationRequest()
        val createDto = commonTestHelper.buildTokenAuthenticationCreateWithClientOrganizationDto(
                userUuid = request.userUuid,
                token = request.token,
                clientUuid = request.clientUuid,
                expiration = request.expiration
        )
        val authToken = commonTestHelper.buildTokenAuthenticationWithOrganization()
        expect(preconditionCheckerComponent.checkPersistWithClientOrganization(request)).andReturn(SingleErrorWithStatus.empty())
        expect(tokenAuthenticationService.createWithClientOrganization(createDto)).andReturn(authToken)
        replayAll()
        assertBasicSuccessResultResponse(tokenAuthenticationServiceFacade.persistWithClientOrganization(request))
        verifyAll()
    }
}
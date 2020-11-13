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
class TokenAuthenticationPersistWithOrganizationFacadeUnitTest : AbstractTokenAuthenticationServiceFacadeUnitTest() {

    @Test
    fun `test when user not found`() {
        resetAll()
        val request = restTestHelper.buildTokenAuthenticationPersistWithOrganizationRequest()
        expect(preconditionCheckerComponent.checkPersistWithOrganization(request)).andReturn(SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, TokenAuthenticationErrorResponseModel.USER_NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(tokenAuthenticationServiceFacade.persistWithOrganization(request), TokenAuthenticationErrorResponseModel.USER_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun `test when organization not found`() {
        resetAll()
        val request = restTestHelper.buildTokenAuthenticationPersistWithOrganizationRequest()
        expect(preconditionCheckerComponent.checkPersistWithOrganization(request)).andReturn(SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, TokenAuthenticationErrorResponseModel.ORGANIZATION_NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(tokenAuthenticationServiceFacade.persistWithOrganization(request), TokenAuthenticationErrorResponseModel.ORGANIZATION_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildTokenAuthenticationPersistWithOrganizationRequest()
        val createDto = commonTestHelper.buildTokenAuthenticationCreateWithOrganizationDto(
                userUuid = request.userUuid,
                token = request.token,
                organizationUuid = request.organizationUuid,
                expiration = request.expiration
        )
        val authToken = commonTestHelper.buildTokenAuthenticationWithOrganization()
        expect(preconditionCheckerComponent.checkPersistWithOrganization(request)).andReturn(SingleErrorWithStatus.empty())
        expect(tokenAuthenticationService.createWithOrganization(createDto)).andReturn(authToken)
        replayAll()
        assertBasicSuccessResultResponse(tokenAuthenticationServiceFacade.persistWithOrganization(request))
        verifyAll()
    }
}
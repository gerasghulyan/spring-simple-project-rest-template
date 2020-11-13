package com.vntana.core.rest.resource.token.auth.impl

import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel
import com.vntana.core.rest.resource.token.auth.AbstractTokenAuthenticationWebTest
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:57 AM
 */
class TokenAuthenticationWithClientOrganizationPersistWebTest : AbstractTokenAuthenticationWebTest() {

    @Test
    fun `test with invalid request`() {
        assertBasicErrorResultResponse(
                tokenAuthenticationResourceClient.persistWithClientOrganization(resourceTestHelper.buildTokenAuthenticationPersistWithClientOrganizationRequest(userUuid = null)),
                TokenAuthenticationErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                tokenAuthenticationResourceClient.persistWithClientOrganization(resourceTestHelper.buildTokenAuthenticationPersistWithClientOrganizationRequest(userUuid = emptyString())),
                TokenAuthenticationErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                tokenAuthenticationResourceClient.persistWithClientOrganization(resourceTestHelper.buildTokenAuthenticationPersistWithClientOrganizationRequest(token = null)),
                TokenAuthenticationErrorResponseModel.MISSING_TOKEN
        )
        assertBasicErrorResultResponse(
                tokenAuthenticationResourceClient.persistWithClientOrganization(resourceTestHelper.buildTokenAuthenticationPersistWithClientOrganizationRequest(token = emptyString())),
                TokenAuthenticationErrorResponseModel.MISSING_TOKEN
        )
        assertBasicErrorResultResponse(
                tokenAuthenticationResourceClient.persistWithClientOrganization(resourceTestHelper.buildTokenAuthenticationPersistWithClientOrganizationRequest(clientUuid = null)),
                TokenAuthenticationErrorResponseModel.MISSING_CLIENT_ORGANIZATION
        )
        assertBasicErrorResultResponse(
                tokenAuthenticationResourceClient.persistWithClientOrganization(resourceTestHelper.buildTokenAuthenticationPersistWithClientOrganizationRequest(clientUuid = emptyString())),
                TokenAuthenticationErrorResponseModel.MISSING_CLIENT_ORGANIZATION
        )
    }

    @Test
    fun `test when user not found`() {
        val request = resourceTestHelper.buildTokenAuthenticationPersistWithClientOrganizationRequest()
        assertBasicErrorResultResponse(tokenAuthenticationResourceClient.persistWithClientOrganization(request), TokenAuthenticationErrorResponseModel.USER_NOT_FOUND)
    }

    @Test
    fun `test when client organization not found`() {
        val request = resourceTestHelper.buildTokenAuthenticationPersistWithClientOrganizationRequest(userUuid = userResourceTestHelper.persistUser().response().uuid)
        assertBasicErrorResultResponse(tokenAuthenticationResourceClient.persistWithClientOrganization(request), TokenAuthenticationErrorResponseModel.CLIENT_ORGANIZATION_NOT_FOUND)
    }

    @Test
    fun test() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val clientUuid = clientOrganizationResourceTestHelper.persistClientOrganization().response().uuid
        val request = resourceTestHelper.buildTokenAuthenticationPersistWithClientOrganizationRequest(userUuid = userUuid, clientUuid = clientUuid)
        assertBasicSuccessResultResponse(tokenAuthenticationResourceClient.persistWithClientOrganization(request))
    }
}
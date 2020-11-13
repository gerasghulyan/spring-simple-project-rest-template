package com.vntana.core.rest.resource.token.auth.impl

import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel
import com.vntana.core.rest.resource.token.auth.AbstractTokenAuthenticationWebTest
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:57 AM
 */
class TokenAuthenticationWithOrganizationPersistWebTest : AbstractTokenAuthenticationWebTest() {

    @Test
    fun `test with invalid request`() {
        assertBasicErrorResultResponse(
                tokenAuthenticationResourceClient.persistWithOrganization(resourceTestHelper.buildTokenAuthenticationPersistWithOrganizationRequest(userUuid = null)),
                TokenAuthenticationErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                tokenAuthenticationResourceClient.persistWithOrganization(resourceTestHelper.buildTokenAuthenticationPersistWithOrganizationRequest(userUuid = emptyString())),
                TokenAuthenticationErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                tokenAuthenticationResourceClient.persistWithOrganization(resourceTestHelper.buildTokenAuthenticationPersistWithOrganizationRequest(token = null)),
                TokenAuthenticationErrorResponseModel.MISSING_TOKEN
        )
        assertBasicErrorResultResponse(
                tokenAuthenticationResourceClient.persistWithOrganization(resourceTestHelper.buildTokenAuthenticationPersistWithOrganizationRequest(token = emptyString())),
                TokenAuthenticationErrorResponseModel.MISSING_TOKEN
        )
        assertBasicErrorResultResponse(
                tokenAuthenticationResourceClient.persistWithOrganization(resourceTestHelper.buildTokenAuthenticationPersistWithOrganizationRequest(organizationUuid = null)),
                TokenAuthenticationErrorResponseModel.MISSING_ORGANIZATION
        )
        assertBasicErrorResultResponse(
                tokenAuthenticationResourceClient.persistWithOrganization(resourceTestHelper.buildTokenAuthenticationPersistWithOrganizationRequest(organizationUuid = emptyString())),
                TokenAuthenticationErrorResponseModel.MISSING_ORGANIZATION
        )
    }

    @Test
    fun `test when user not found`() {
        val request = resourceTestHelper.buildTokenAuthenticationPersistWithOrganizationRequest()
        assertBasicErrorResultResponse(tokenAuthenticationResourceClient.persistWithOrganization(request), TokenAuthenticationErrorResponseModel.USER_NOT_FOUND)
    }

    @Test
    fun `test when organization not found`() {
        val request = resourceTestHelper.buildTokenAuthenticationPersistWithOrganizationRequest(userUuid = userResourceTestHelper.persistUser().response().uuid)
        assertBasicErrorResultResponse(tokenAuthenticationResourceClient.persistWithOrganization(request), TokenAuthenticationErrorResponseModel.ORGANIZATION_NOT_FOUND)
    }

    @Test
    fun test() {
        val response = userResourceTestHelper.persistUser().response()
        val userUuid = response.uuid
        val organizationUuid = response.organizationUuid
        val request = resourceTestHelper.buildTokenAuthenticationPersistWithOrganizationRequest(userUuid = userUuid, organizationUuid = organizationUuid)
        assertBasicSuccessResultResponse(tokenAuthenticationResourceClient.persistWithOrganization(request))
    }
}
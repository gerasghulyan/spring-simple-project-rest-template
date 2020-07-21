package com.vntana.core.rest.resource.token.auth.impl

import com.vntana.core.model.token.auth.error.AuthTokenErrorResponseModel
import com.vntana.core.rest.resource.token.auth.AbstractAuthTokenWebTest
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:57 AM
 */
class AuthTokenWithOrganizationPersistWebTest : AbstractAuthTokenWebTest() {

    @Test
    fun `test with invalid request`() {
        assertBasicErrorResultResponse(
                authTokenResourceClient.persistWithOrganization(resourceTestHelper.buildAuthTokenPersistWithOrganizationRequest(userUuid = null)),
                AuthTokenErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                authTokenResourceClient.persistWithOrganization(resourceTestHelper.buildAuthTokenPersistWithOrganizationRequest(userUuid = "")),
                AuthTokenErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                authTokenResourceClient.persistWithOrganization(resourceTestHelper.buildAuthTokenPersistWithOrganizationRequest(token = null)),
                AuthTokenErrorResponseModel.MISSING_TOKEN
        )
        assertBasicErrorResultResponse(
                authTokenResourceClient.persistWithOrganization(resourceTestHelper.buildAuthTokenPersistWithOrganizationRequest(token = "")),
                AuthTokenErrorResponseModel.MISSING_TOKEN
        )
        assertBasicErrorResultResponse(
                authTokenResourceClient.persistWithOrganization(resourceTestHelper.buildAuthTokenPersistWithOrganizationRequest(organizationUuid = null)),
                AuthTokenErrorResponseModel.MISSING_ORGANIZATION
        )
        assertBasicErrorResultResponse(
                authTokenResourceClient.persistWithOrganization(resourceTestHelper.buildAuthTokenPersistWithOrganizationRequest(organizationUuid = "")),
                AuthTokenErrorResponseModel.MISSING_ORGANIZATION
        )
    }

    @Test
    fun `test when user not found`() {
        val request = resourceTestHelper.buildAuthTokenPersistWithOrganizationRequest()
        assertBasicErrorResultResponse(authTokenResourceClient.persistWithOrganization(request), AuthTokenErrorResponseModel.USER_NOT_FOUND)
    }

    @Test
    fun test() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val request = resourceTestHelper.buildAuthTokenPersistWithOrganizationRequest(userUuid = userUuid, organizationUuid = organizationUuid)
        assertBasicSuccessResultResponse(authTokenResourceClient.persistWithOrganization(request))
    }
}
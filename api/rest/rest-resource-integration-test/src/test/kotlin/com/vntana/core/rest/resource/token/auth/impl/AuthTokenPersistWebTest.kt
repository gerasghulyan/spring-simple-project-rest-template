package com.vntana.core.rest.resource.token.auth.impl

import com.vntana.core.model.token.auth.error.AuthTokenErrorResponseModel
import com.vntana.core.rest.resource.token.auth.AbstractAuthTokenWebTest
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:57 AM
 */
class AuthTokenPersistWebTest : AbstractAuthTokenWebTest() {

    @Test
    fun `test with invalid request`() {
        assertBasicErrorResultResponse(
                authTokenResourceClient.persist(resourceTestHelper.buildAuthTokenPersistRequest(userUuid = null)),
                AuthTokenErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                authTokenResourceClient.persist(resourceTestHelper.buildAuthTokenPersistRequest(token = null)),
                AuthTokenErrorResponseModel.MISSING_TOKEN
        )
    }

    @Test
    fun `test when user not found`() {
        val request = resourceTestHelper.buildAuthTokenPersistRequest()
        assertBasicErrorResultResponse(authTokenResourceClient.persist(request), AuthTokenErrorResponseModel.USER_NOT_FOUND)
    }

    @Test
    fun test() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val request = resourceTestHelper.buildAuthTokenPersistRequest(userUuid = userUuid)
        assertBasicSuccessResultResponse(authTokenResourceClient.persist(request))
    }
}
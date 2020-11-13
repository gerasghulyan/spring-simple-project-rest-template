package com.vntana.core.rest.resource.token.auth.impl

import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel
import com.vntana.core.rest.resource.token.auth.AbstractTokenAuthenticationWebTest
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:57 AM
 */
class TokenAuthenticationPersistWebTest : AbstractTokenAuthenticationWebTest() {

    @Test
    fun `test with invalid request`() {
        assertBasicErrorResultResponse(
                tokenAuthenticationResourceClient.persist(resourceTestHelper.buildTokenAuthenticationPersistRequest(userUuid = null)),
                TokenAuthenticationErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                tokenAuthenticationResourceClient.persist(resourceTestHelper.buildTokenAuthenticationPersistRequest(token = null)),
                TokenAuthenticationErrorResponseModel.MISSING_TOKEN
        )
    }

    @Test
    fun `test when user not found`() {
        val request = resourceTestHelper.buildTokenAuthenticationPersistRequest()
        assertBasicErrorResultResponse(tokenAuthenticationResourceClient.persist(request), TokenAuthenticationErrorResponseModel.USER_NOT_FOUND)
    }

    @Test
    fun test() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val request = resourceTestHelper.buildTokenAuthenticationPersistRequest(userUuid = userUuid)
        assertBasicSuccessResultResponse(tokenAuthenticationResourceClient.persist(request))
    }
}
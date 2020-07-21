package com.vntana.core.rest.resource.token.auth.impl

import com.vntana.core.model.token.auth.error.AuthTokenErrorResponseModel
import com.vntana.core.rest.resource.token.auth.AbstractAuthTokenWebTest
import org.junit.Test

/**
 * Created by Geras Ghulyan
 * Date: 7/15/20
 * Time: 3:27 PM
 */
class AuthTokenFindByTokenWebTest : AbstractAuthTokenWebTest() {

    @Test
    fun `test when token not found`() {
        assertBasicErrorResultResponse(authTokenResourceClient.findByToken(resourceTestHelper.buildAuthTokenRequest()), AuthTokenErrorResponseModel.TOKEN_NOT_FOUND)
    }

    @Test
    fun test() {
        val token = resourceTestHelper.persistToken()
        authTokenResourceClient.findByToken(resourceTestHelper.buildAuthTokenRequest(token = token)).let {
            assertBasicSuccessResultResponse(it)
        }
    }
}
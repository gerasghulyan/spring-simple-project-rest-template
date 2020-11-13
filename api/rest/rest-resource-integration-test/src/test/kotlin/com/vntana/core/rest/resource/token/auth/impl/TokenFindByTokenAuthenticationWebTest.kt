package com.vntana.core.rest.resource.token.auth.impl

import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel
import com.vntana.core.rest.resource.token.auth.AbstractTokenAuthenticationWebTest
import org.junit.Test

/**
 * Created by Geras Ghulyan
 * Date: 7/15/20
 * Time: 3:27 PM
 */
class TokenFindByTokenAuthenticationWebTest : AbstractTokenAuthenticationWebTest() {

    @Test
    fun `test when token not found`() {
        assertBasicErrorResultResponse(tokenAuthenticationResourceClient.findByToken(resourceTestHelper.buildTokenAuthenticationRequest()), TokenAuthenticationErrorResponseModel.TOKEN_NOT_FOUND)
    }

    @Test
    fun test() {
        val token = resourceTestHelper.persistToken()
        tokenAuthenticationResourceClient.findByToken(resourceTestHelper.buildTokenAuthenticationRequest(token = token)).let {
            assertBasicSuccessResultResponse(it)
        }
    }
}
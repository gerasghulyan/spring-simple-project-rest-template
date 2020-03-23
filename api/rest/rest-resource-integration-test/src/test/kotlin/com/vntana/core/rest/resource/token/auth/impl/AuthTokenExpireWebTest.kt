package com.vntana.core.rest.resource.token.auth.impl

import com.vntana.core.model.token.auth.error.AuthTokenErrorResponseModel
import com.vntana.core.rest.resource.token.auth.AbstractAuthTokenWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 12:57 PM
 */
class AuthTokenExpireWebTest : AbstractAuthTokenWebTest() {

    @Test
    fun `test when token not found`() {
        assertBasicErrorResultResponse(authTokenResourceClient.expire(uuid()), AuthTokenErrorResponseModel.TOKEN_NOT_FOUND)
    }

    @Test
    fun test() {
        val token1 = resourceTestHelper.persistToken()
        val token2 = resourceTestHelper.persistToken()
        authTokenResourceClient.expire(token1).let {
            assertBasicSuccessResultResponse(it)
            assertThat(authTokenResourceClient.isExpired(token1)?.body?.response()?.expired).isTrue()
            assertThat(authTokenResourceClient.isExpired(token2)?.body?.response()?.expired).isFalse()
        }
    }
}
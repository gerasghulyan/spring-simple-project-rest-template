package com.vntana.core.rest.resource.token.auth.impl

import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel
import com.vntana.core.rest.resource.token.auth.AbstractTokenAuthenticationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 12:57 PM
 */
class TokenAuthenticationExpireWebTest : AbstractTokenAuthenticationWebTest() {

    @Test
    fun `test when token not found`() {
        assertBasicErrorResultResponse(
            tokenAuthenticationResourceClient.expire(uuid()),
            TokenAuthenticationErrorResponseModel.TOKEN_NOT_FOUND
        )
    }

    @Ignore
    @Test
    fun test() {
        val token1 = resourceTestHelper.persistToken()
        val token2 = resourceTestHelper.persistToken()
        tokenAuthenticationResourceClient.expire(token1).let {
            assertBasicSuccessResultResponse(it)
            assertThat(tokenAuthenticationResourceClient.isExpired(token1)?.body?.response()?.expired).isTrue()
            assertThat(tokenAuthenticationResourceClient.isExpired(token2)?.body?.response()?.expired).isFalse()
        }
    }
}
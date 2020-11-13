package com.vntana.core.rest.resource.token.auth.impl

import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel
import com.vntana.core.rest.resource.token.auth.AbstractTokenAuthenticationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 12:32 PM
 */
class TokenAuthenticationIsExpiredWebTest : AbstractTokenAuthenticationWebTest() {

    @Test
    fun `test when token does not exist`() {
        assertBasicErrorResultResponse(tokenAuthenticationResourceClient.isExpired(uuid()), TokenAuthenticationErrorResponseModel.TOKEN_NOT_FOUND)
    }

    @Test
    fun `test when not expired`() {
        val token = resourceTestHelper.persistToken()
        tokenAuthenticationResourceClient.isExpired(token).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it?.body?.response()?.expired).isFalse()
        }
    }
}
package com.vntana.core.rest.resource.token.auth.impl

import com.vntana.core.model.token.auth.error.AuthTokenErrorResponseModel
import com.vntana.core.rest.resource.token.auth.AbstractAuthTokenWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 12:32 PM
 */
class AuthTokenIsExpiredWebTest : AbstractAuthTokenWebTest() {

    @Test
    fun `test when token does not exist`() {
        assertBasicErrorResultResponse(authTokenResourceClient.isExpired(uuid()), AuthTokenErrorResponseModel.TOKEN_NOT_FOUND)
    }

    @Test
    fun `test when not expired`() {
        val token = resourceTestHelper.persistToken()
        authTokenResourceClient.isExpired(token).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it?.body?.response()?.expired).isFalse()
        }
    }
}
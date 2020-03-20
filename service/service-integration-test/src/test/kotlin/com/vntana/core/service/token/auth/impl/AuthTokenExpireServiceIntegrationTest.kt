package com.vntana.core.service.token.auth.impl

import com.vntana.core.service.token.auth.AbstractAuthTokenServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 4:20 PM
 */
class AuthTokenExpireServiceIntegrationTest : AbstractAuthTokenServiceIntegrationTest() {

    @Test
    fun test() {
        val authToken = integrationTestHelper.persistAuthToken()
        flushAndClear()
        authTokenService.expire(authToken.uuid).let {
            flushAndClear()
            assertThat(it.isExpired).isTrue()
        }
    }
}
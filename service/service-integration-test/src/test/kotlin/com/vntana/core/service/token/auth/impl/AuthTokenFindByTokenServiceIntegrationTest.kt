package com.vntana.core.service.token.auth.impl

import com.vntana.core.service.token.auth.AbstractAuthTokenServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:13 AM
 */
class AuthTokenFindByTokenServiceIntegrationTest : AbstractAuthTokenServiceIntegrationTest() {

    @Test
    fun `test whn not found`() {
        assertThat(authTokenService.findByToken(uuid())).isEmpty
    }

    @Test
    fun test() {
        val token = uuid()
        val authToken = integrationTestHelper.persistAuthToken(token = token)
        integrationTestHelper.persistAuthToken()
        flushAndClear()
        authTokenService.findByToken(token).let {
            assertThat(it).isPresent
            assertThat(it.get()).isEqualTo(authToken)
        }
    }
}
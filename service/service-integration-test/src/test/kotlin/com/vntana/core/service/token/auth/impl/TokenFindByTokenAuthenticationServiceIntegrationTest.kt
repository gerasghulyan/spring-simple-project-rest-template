package com.vntana.core.service.token.auth.impl

import com.vntana.core.service.token.auth.AbstractTokenAuthenticationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:13 AM
 */
class TokenFindByTokenAuthenticationServiceIntegrationTest : AbstractTokenAuthenticationServiceIntegrationTest() {

    @Test
    fun `test whn not found`() {
        assertThat(tokenAuthenticationService.findByToken(uuid())).isEmpty
    }

    @Test
    fun test() {
        val token = uuid()
        val authToken = integrationTestHelper.persistAuthToken(token = token)
        integrationTestHelper.persistAuthToken()
        flushAndClear()
        tokenAuthenticationService.findByToken(token).let {
            assertThat(it).isPresent
            assertThat(it.get()).isEqualTo(authToken)
        }
    }
}
package com.vntana.core.service.token.auth.impl

import com.vntana.core.service.token.auth.AbstractTokenAuthenticationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 4:03 PM
 */
class TokenAuthenticationFindByUuidServiceIntegrationTest : AbstractTokenAuthenticationServiceIntegrationTest() {

    @Test
    fun `test when not found`() {
        assertThat(tokenAuthenticationService.findByUuid(uuid())).isEmpty
    }

    @Test
    fun `test when found`() {
        val authToken = integrationTestHelper.persistAuthToken()
        flushAndClear()
        tokenAuthenticationService.findByUuid(authToken.uuid).let {
            assertThat(it).isPresent
            assertThat(it.get()).isEqualTo(authToken)
        }
    }
}
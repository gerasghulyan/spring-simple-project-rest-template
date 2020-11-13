package com.vntana.core.service.token.auth.impl

import com.vntana.core.service.token.auth.AbstractTokenAuthenticationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 4:16 PM
 */
class TokenAuthenticationGetByUuidServiceIntegrationTest : AbstractTokenAuthenticationServiceIntegrationTest() {

    @Test
    fun test() {
        val authToken = integrationTestHelper.persistAuthToken()
        flushAndClear()
        assertThat(tokenAuthenticationService.getByUuid(authToken.uuid)).isEqualTo(authToken)
    }
}
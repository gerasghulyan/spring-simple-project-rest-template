package com.vntana.core.service.token.auth.impl

import com.vntana.core.service.token.auth.AbstractAuthTokenServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 3:18 PM
 */
class AuthTokenCreateServiceIntegrationTest : AbstractAuthTokenServiceIntegrationTest() {

    @Test
    fun test() {
        val user = userIntegrationTestHelper.persistUser()
        val dto = integrationTestHelper.buildAuthTokenCreateDto(userUuid = user.uuid)
        authTokenService.create(dto).let {
            flushAndClear()
            assertThat(it.isExpired).isFalse()
            assertThat(it.user).isEqualTo(user)
            assertThat(it.token).isEqualTo(dto.token)
        }
    }
}
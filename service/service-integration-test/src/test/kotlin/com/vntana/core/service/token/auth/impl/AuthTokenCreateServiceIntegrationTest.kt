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
        val user = userIntegrationTestHelper.persistUserWithOwnerRole()
        val dto = integrationTestHelper.buildAuthTokenCreateDto(userUuid = user.uuid)
        val token = authTokenService.create(dto)
        flushAndClear()
        assertThat(token.isExpired).isFalse()
        assertThat(token.user).isEqualTo(user)
        assertThat(token.token).isEqualTo(dto.token)
    }
}
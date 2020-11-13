package com.vntana.core.service.token.auth.impl

import com.vntana.core.service.token.auth.AbstractTokenAuthenticationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 3:18 PM
 */
class TokenAuthenticationCreateServiceIntegrationTest : AbstractTokenAuthenticationServiceIntegrationTest() {

    @Test
    fun test() {
        val user = userIntegrationTestHelper.persistUserWithOwnerRole()
        val dto = integrationTestHelper.buildTokenAuthenticationCreateDto(userUuid = user.uuid)
        val token = tokenAuthenticationService.create(dto)
        flushAndClear()
        assertThat(token.isExpired).isFalse()
        assertThat(token.user).isEqualTo(user)
        assertThat(token.token).isEqualTo(dto.token)
    }
}
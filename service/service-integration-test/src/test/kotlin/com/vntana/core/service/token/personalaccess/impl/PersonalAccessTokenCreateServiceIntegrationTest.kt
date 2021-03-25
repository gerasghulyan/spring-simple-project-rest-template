package com.vntana.core.service.token.personalaccess.impl

import com.vntana.core.service.token.personalaccess.AbstractPersonalAccessTokenServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 12:59 AM
 */
class PersonalAccessTokenCreateServiceIntegrationTest : AbstractPersonalAccessTokenServiceIntegrationTest() {

    @Test
    fun test() {
        val token = uuid()
        val user = userIntegrationTestHelper.persistUser()
        val dto =
            integrationTestHelper.buildTokenPersonalAccessCreateDto(user.uuid, token)
        flushAndClear()
        personalAccessTokenService.create(dto).let {
            assertThat(it.user).isEqualTo(user)
            assertThat(it.token).isEqualTo(token)
        }
    }
}
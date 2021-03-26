package com.vntana.core.service.token.personalaccess.impl

import com.vntana.core.service.token.personalaccess.AbstractPersonalAccessTokenServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 9:17 AM
 */
class PersonalAccessTokenExpireServiceIntegrationTest : AbstractPersonalAccessTokenServiceIntegrationTest() {

    @Test
    fun test() {
        val token = uuid()
        val user = userIntegrationTestHelper.persistUser()
        val pat =
            integrationTestHelper.persistPersonalAccessToken(token = token, userUuid = user.uuid)
        flushAndClear()
        personalAccessTokenService.expire(pat.uuid).let {
            assertThat(it.user).isEqualTo(user)
            assertThat(it.token).isEqualTo(token)
            assertThat(it.isExpired).isTrue()
        }
    }
}
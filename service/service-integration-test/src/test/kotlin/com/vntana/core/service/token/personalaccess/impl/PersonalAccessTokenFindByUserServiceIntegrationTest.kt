package com.vntana.core.service.token.personalaccess.impl

import com.vntana.core.service.token.personalaccess.AbstractPersonalAccessTokenServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 8:32 AM
 */
class PersonalAccessTokenFindByUserServiceIntegrationTest : AbstractPersonalAccessTokenServiceIntegrationTest() {

    @Test
    fun test() {
        val user = userIntegrationTestHelper.persistUser()
        val expiredToken = uuid()
        val token = uuid()
        integrationTestHelper.persistPersonalAccessToken(token = expiredToken, userUuid = user.uuid).let {
            integrationTestHelper.expirePersonalAccessToken(it.uuid)

        }
        integrationTestHelper.persistPersonalAccessToken(token = token, userUuid = user.uuid)
        flushAndClear()
        personalAccessTokenService.findByUser(user.uuid).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.get().user).isEqualTo(user)
            assertThat(it.get().token).isEqualTo(token)
        }
    }
}
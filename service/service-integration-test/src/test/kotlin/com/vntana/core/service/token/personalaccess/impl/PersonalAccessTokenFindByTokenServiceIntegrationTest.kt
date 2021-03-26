package com.vntana.core.service.token.personalaccess.impl

import com.vntana.core.service.token.personalaccess.AbstractPersonalAccessTokenServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 9:29 AM
 */
class PersonalAccessTokenFindByTokenServiceIntegrationTest : AbstractPersonalAccessTokenServiceIntegrationTest() {

    @Test
    fun `test when token is valid`() {
        val user = userIntegrationTestHelper.persistUser()
        val token = uuid()
        integrationTestHelper.persistPersonalAccessToken(token = token, userUuid = user.uuid)
        flushAndClear()
        personalAccessTokenService.findByToken(token).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.get().user).isEqualTo(user)
            assertThat(it.get().token).isEqualTo(token)
        }
    }

    @Test
    fun `test when token is expired`() {
        val user = userIntegrationTestHelper.persistUser()
        val token = uuid()
        integrationTestHelper.persistPersonalAccessToken(token = token, userUuid = user.uuid).let {
            integrationTestHelper.expirePersonalAccessToken(it.uuid)
        }
        flushAndClear()
        personalAccessTokenService.findByToken(token).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.get().user).isEqualTo(user)
            assertThat(it.get().token).isEqualTo(token)
            assertThat(it.get().isExpired).isEqualTo(true)
        }
    }
}
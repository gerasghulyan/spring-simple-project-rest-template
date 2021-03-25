package com.vntana.core.service.token.personalaccess.impl

import com.vntana.core.service.token.personalaccess.AbstractPersonalAccessTokenServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 9:44 AM
 */
class PersonalAccessTokenRegenerateTokenForUserServiceIntegrationTest :
    AbstractPersonalAccessTokenServiceIntegrationTest() {

    @Test
    fun test() {
        val oldToken = uuid()
        val newToken = uuid()
        val user = userIntegrationTestHelper.persistUser()
        val dto = integrationTestHelper.buildTokenPersonalAccessRegenerateDto(userUuid = user.uuid, token = newToken)
        integrationTestHelper.persistPersonalAccessToken(token = oldToken, userUuid = user.uuid)
        flushAndClear()
        personalAccessTokenService.regenerateTokenForUser(dto).let {
            assertThat(it.user).isEqualTo(user)
            assertThat(it.token).isEqualTo(newToken)
        }
        personalAccessTokenService.findByUser(user.uuid).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.get().user).isEqualTo(user)
            assertThat(it.get().token).isEqualTo(newToken)
        }
    }
}
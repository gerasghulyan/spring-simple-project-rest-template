package com.vntana.core.service.token.test

import com.vntana.core.service.token.AbstractTokenServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/22/19
 * Time: 12:50 PM
 */
class TokenFindByTokenServiceIntegrationTest : AbstractTokenServiceIntegrationTest() {
    @Test
    fun `test findByToken`() {
        // given
        integrationTestHelper.persistResetPasswordToken().let { token ->
            flushAndClear()
            // when
            tokenService.findByToken(token.token).let {
                // then
                assertThat(it).hasValue(token)
            }
        }
    }
}
package com.vntana.core.service.token.test

import com.vntana.core.service.token.AbstractTokenServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/22/19
 * Time: 12:50 PM
 */
class TokenFindByUuidServiceIntegrationTest : AbstractTokenServiceIntegrationTest() {
    @Test
    fun `test findByUuid`() {
        // given
        integrationTestHelper.persistResetPasswordToken().let { token ->
            flushAndClear()
            // when
            tokenService.findByUuid(token.uuid).let {
                // then
                assertThat(it).hasValue(token)
            }
        }
    }
}
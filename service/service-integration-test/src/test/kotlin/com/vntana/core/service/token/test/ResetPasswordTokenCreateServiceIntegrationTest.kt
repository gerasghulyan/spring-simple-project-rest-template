package com.vntana.core.service.token.test

import com.vntana.core.helper.integration.user.UserServiceIntegrationTestHelper
import com.vntana.core.service.token.AbstractTokenServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 3:04 PM
 */
class ResetPasswordTokenCreateServiceIntegrationTest : AbstractTokenServiceIntegrationTest() {

    @Autowired
    private lateinit var userIntegrationTestHelper: UserServiceIntegrationTestHelper

    @Test
    fun `test create`() {
        // given
        userIntegrationTestHelper.persistUser().let { user ->
            flushAndClear()
            // and
            unitTestHelper.buildCreateResetPasswordTokenDto(userUuid = user.uuid).let { dto ->
                // when
                resetPasswordTokenService.create(dto).let {
                    // then
                    assertThat(it).isNotNull.hasFieldOrPropertyWithValue("user", user)
                }
            }
        }
    }
}
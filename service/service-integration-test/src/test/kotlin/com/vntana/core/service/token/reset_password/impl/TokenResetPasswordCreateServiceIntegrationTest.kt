package com.vntana.core.service.token.reset_password.impl

import com.vntana.core.service.token.reset_password.AbstractTokenResetPasswordServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Geras Ghulyan
 * Date: 6/23/20
 * Time: 2:41 PM
 */
class TokenResetPasswordCreateServiceIntegrationTest : AbstractTokenResetPasswordServiceIntegrationTest() {

    @Test
    fun test() {
        // test data
        val user = userIntegrationTestHelper.persistUser()
        val dto = commonTestHelper.buildCreateTokenResetPasswordDto(userUuid = user.uuid)
        // test scenario
        val token = tokenResetPasswordService.create(dto)
        flushAndClear()
        assertThat(token.user.uuid).isEqualTo(user.uuid)
    }

}
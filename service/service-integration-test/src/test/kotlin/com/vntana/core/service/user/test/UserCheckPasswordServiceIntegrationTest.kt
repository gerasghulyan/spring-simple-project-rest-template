package com.vntana.core.service.user.test

import com.vntana.core.service.user.AbstractUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 1/24/2020
 * Time: 10:56 AM
 */
class UserCheckPasswordServiceIntegrationTest : AbstractUserServiceIntegrationTest() {

    @Test
    fun `test check password`() {
        val oldPassword = uuid()
        val user = integrationTestHelper.persistUser(password = oldPassword)
        flushAndClear()
        assertThat(userService.checkPassword(user.uuid, oldPassword)).isTrue()
    }
}
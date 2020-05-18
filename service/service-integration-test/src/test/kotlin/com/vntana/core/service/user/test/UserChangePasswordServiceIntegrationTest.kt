package com.vntana.core.service.user.test

import com.vntana.core.service.user.AbstractUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 12/12/19
 * Time: 6:08 PM
 */
class UserChangePasswordServiceIntegrationTest : AbstractUserServiceIntegrationTest() {

    @Test
    fun test() {
        val newPassword = uuid()
        val user = integrationTestHelper.persistUser()
        flushAndClear()
        userService.changePassword(user.uuid, newPassword).let {
            flushAndClear()
            assertThat(it.uuid).isEqualTo(user.uuid)
            assertThat(passwordEncoder.matches(newPassword, it.password)).isTrue()
        }

    }
}
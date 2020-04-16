package com.vntana.core.service.user.test

import com.vntana.core.service.user.AbstractUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 4:59 PM
 */
class UserGetByEmailServiceIntegrationTest : AbstractUserServiceIntegrationTest() {

    @Test
    fun test() {
        // given
        integrationTestHelper.persistUser().let { user ->
            flushAndClear()
            // when
            userService.getByEmail(user.email).let {
                // then
                assertThat(it).isEqualTo(user)
            }
        }
    }

}
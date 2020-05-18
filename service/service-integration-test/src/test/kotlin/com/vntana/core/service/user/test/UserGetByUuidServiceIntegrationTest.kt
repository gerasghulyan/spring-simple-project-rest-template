package com.vntana.core.service.user.test

import com.vntana.core.service.user.AbstractUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Geras Ghulyan.
 * Date: 10/18/19
 * Time: 4:59 PM
 */
class UserGetByUuidServiceIntegrationTest : AbstractUserServiceIntegrationTest() {

    @Test
    fun `test getByUuid`() {
        // given
        integrationTestHelper.persistUserWithOwnerRole().let { user ->
            flushAndClear()
            // when
            userService.getByUuid(user.uuid).let {
                // then
                assertThat(it).isEqualTo(user)
            }
        }
    }

}
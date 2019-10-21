package com.vntana.core.service.user.test

import com.vntana.core.service.user.AbstractUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 4:59 PM
 */
class UserFindByUuidServiceIntegrationTest : AbstractUserServiceIntegrationTest() {

    @Test
    fun `test findByUuid`() {
        // given
        integrationTestHelper.persistUser().let { user ->
            flushAndClear()
            // when
            userService.findByUuid(user.uuid).let {
                // then
                assertThat(it).hasValue(user)
            }
        }
    }

}
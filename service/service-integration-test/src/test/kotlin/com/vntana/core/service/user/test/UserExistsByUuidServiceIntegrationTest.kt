package com.vntana.core.service.user.test

import com.vntana.core.service.user.AbstractUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 1/23/2020
 * Time: 5:12 PM
 */
class UserExistsByUuidServiceIntegrationTest : AbstractUserServiceIntegrationTest() {

    @Test
    fun `test exists`() {
        val user = integrationTestHelper.persistUser()
        assertThat(userService.existsByUuid(user.uuid)).isTrue()
    }

    @Test
    fun `test not exist`() {
        assertThat(userService.existsByUuid(uuid())).isFalse()
    }
}
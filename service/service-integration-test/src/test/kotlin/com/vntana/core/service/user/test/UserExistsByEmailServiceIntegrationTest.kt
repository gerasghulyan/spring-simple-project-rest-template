package com.vntana.core.service.user.test

import com.vntana.core.service.user.AbstractUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 4/6/20
 * Time: 10:33 AM
 */
class UserExistsByEmailServiceIntegrationTest : AbstractUserServiceIntegrationTest() {

    @Test
    fun `test does not exist`() {
        assertThat(userService.existsByEmail(uuid())).isFalse()
    }
    
    @Test
    fun `test exists`() {
        val user = integrationTestHelper.persistUser()
        assertThat(userService.existsByEmail(user.email)).isTrue()
    }

}
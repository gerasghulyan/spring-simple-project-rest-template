package com.vntana.core.service.user.test

import com.vntana.core.service.user.AbstractUserServiceIntegrationTest
import com.vntana.core.service.user.exception.UserAlreadyVerifiedException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 11/11/19
 * Time: 2:44 PM
 */
class UserMakeVerifiedServiceIntegrationTest : AbstractUserServiceIntegrationTest() {

    @Test
    fun `test when user not found`() {
        assertThatThrownBy { userService.makeVerified(uuid()) }
                .isExactlyInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun `test when user already verified`() {
        val user = integrationTestHelper.persistVerifiedUser()
        assertThatThrownBy { userService.makeVerified(user.email) }
                .isExactlyInstanceOf(UserAlreadyVerifiedException::class.java)
    }

    @Test
    fun `test`() {
        val user = integrationTestHelper.persistUser()
        userService.makeVerified(user.email).let {
            assertThat(it.email).isEqualTo(user.email)
            assertThat(it.verified).isTrue()
        }
    }
}
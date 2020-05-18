package com.vntana.core.service.user.test

import com.vntana.core.service.user.AbstractUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/15/20
 * Time: 4:33 PM
 */
class UserCreateServiceIntegrationTest : AbstractUserServiceIntegrationTest() {

    @Test
    fun `test create`() {
        val dto = integrationTestHelper.buildCreateUserDto()
        userService.create(dto).let {
            flushAndClear()
            assertThat(it).isNotNull
            assertThat(it.fullName).isEqualTo(dto.fullName)
            assertThat(it.email).isEqualTo(dto.email)
            assertThat(it.roles()).isEmpty()
            assertThat(passwordEncoder.matches(dto.password, it.password)).isTrue()
        }
    }
}
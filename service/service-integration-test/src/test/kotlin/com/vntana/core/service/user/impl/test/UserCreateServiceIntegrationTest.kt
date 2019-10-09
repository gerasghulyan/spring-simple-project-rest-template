package com.vntana.core.service.user.impl.test

import com.vntana.core.service.user.impl.AbstractUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/4/19
 * Time: 2:27 PM
 */
class UserCreateServiceIntegrationTest : AbstractUserServiceIntegrationTest() {
    @Test
    fun `test create`() {
        val createDto = integrationTestHelper.buildUserCreateDto()
        userService.createUser(createDto).let {
            assertThat(it).isNotNull
            assertThat(it.firstName).isEqualTo(createDto.firstName)
            assertThat(it.secondName).isEqualTo(createDto.secondName)
        }
    }
}
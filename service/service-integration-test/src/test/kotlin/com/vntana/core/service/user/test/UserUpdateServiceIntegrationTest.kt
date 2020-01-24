package com.vntana.core.service.user.test

import com.vntana.core.service.user.AbstractUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 1/23/2020
 * Time: 4:00 PM
 */
class UserUpdateServiceIntegrationTest : AbstractUserServiceIntegrationTest() {

    @Test
    fun `test update`() {
        val user = integrationTestHelper.persistUser()
        flushAndClear()
        val dto = integrationTestHelper.buildUpdateUserDto(uuid = user.uuid)
        userService.update(dto).let {
            assertThat(it).isNotNull
            assertThat(it.uuid).isEqualTo(dto.uuid)
            assertThat(it.imageBlobId).isEqualTo(dto.imageBlobId)
            assertThat(it.fullName).isEqualTo(dto.fullName)
        }
    }
}
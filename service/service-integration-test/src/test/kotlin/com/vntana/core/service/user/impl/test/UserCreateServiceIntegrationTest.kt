package com.vntana.core.service.user.impl.test

import com.vntana.core.helper.integration.client.ClientOrganizationIntegrationTestHelper
import com.vntana.core.service.user.impl.AbstractUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 10/4/19
 * Time: 2:27 PM
 */
class UserCreateServiceIntegrationTest : AbstractUserServiceIntegrationTest() {

    @Autowired
    private lateinit var clientOrganizationIntegrationTestHelper: ClientOrganizationIntegrationTestHelper

    @Test
    fun `test create`() {
        val clientOrganization = clientOrganizationIntegrationTestHelper.persistClientOrganization()
        flushAndClear()
        val createDto = integrationTestHelper.buildUserCreateDto(clientOrganizationUuid = clientOrganization.uuid)
        userService.create(createDto).let {
            flush()
            assertThat(it).isNotNull
            assertThat(it.fullName).isEqualTo(createDto.fullName)
            assertThat(it.email).isEqualTo(createDto.email)
            val role = it.roleOfClient(clientOrganization).get()
            assertThat(role.clientOrganization).isEqualTo(clientOrganization)
            assertThat(role.user).isEqualTo(it)
        }
    }
}
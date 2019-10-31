package com.vntana.core.service.user.test

import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.service.user.AbstractUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
 * Created by Arthur Asatryan.
 * Date: 10/4/19
 * Time: 2:27 PM
 */
class UserCreateServiceIntegrationTest : AbstractUserServiceIntegrationTest() {

    @Autowired
    private lateinit var organizationIntegrationTestHelper: OrganizationIntegrationTestHelper

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    @Test
    fun `test create`() {
        val organization = organizationIntegrationTestHelper.persistOrganization()
        flushAndClear()
        val createDto = integrationTestHelper.buildUserCreateDto(organizationUuid = organization.uuid)
        userService.create(createDto).let {
            flush()
            assertThat(it).isNotNull
            assertThat(it.fullName).isEqualTo(createDto.fullName)
            assertThat(it.email).isEqualTo(createDto.email)
            val role = it.roleOfOrganization(organization).get()
            assertThat(role.organization).isEqualTo(organization)
            assertThat(role.user.fullName).isEqualTo(it.fullName)
            assertThat(passwordEncoder.matches(role.user.password, passwordEncoder.encode(it.password))).isTrue()
            assertThat(role.user.email).isEqualTo(it.email)
        }
    }
}
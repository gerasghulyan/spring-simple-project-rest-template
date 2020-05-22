package com.vntana.core.service.user.test

import com.vntana.core.service.user.AbstractUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/4/19
 * Time: 2:27 PM
 */
class UserCreateWithOwnerRoleServiceIntegrationTest : AbstractUserServiceIntegrationTest() {

    @Test
    fun `test create`() {
        val organization = organizationIntegrationTest.persistOrganization()
        flushAndClear()
        val createDto = integrationTestHelper.buildCreateUserWithOwnerRoleDto(organizationUuid = organization.uuid)
        userService.createWithOwnerRole(createDto).let {
            flushAndClear()
            assertThat(it).isNotNull
            assertThat(it.fullName).isEqualTo(createDto.fullName)
            assertThat(it.email).isEqualTo(createDto.email)
            val role = it.roleOfOrganizationOwner(organization).get()
            assertThat(role.organization).isEqualTo(organization)
            assertThat(role.user.fullName).isEqualTo(it.fullName)
            assertThat(passwordEncoder.matches(role.user.password, passwordEncoder.encode(it.password))).isTrue()
            assertThat(role.user.email).isEqualTo(it.email)
        }
    }
}
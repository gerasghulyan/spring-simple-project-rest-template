package com.vntana.core.service.user.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.user.AbstractUserServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 4/7/2020
 * Time: 2:12 PM
 */
class UserFindByRoleAndOrganizationUuidServiceUnitTest : AbstractUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { userService.findByRoleAndOrganizationUuid(null, uuid()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.findByRoleAndOrganizationUuid(UserRole.ORGANIZATION_ADMIN, null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.findByRoleAndOrganizationUuid(UserRole.ORGANIZATION_ADMIN, emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test find`() {
        val organization = organizationHelper.buildOrganization()
        val user = helper.buildUser(clientOrganization = organization)
        val userRole = UserRole.ORGANIZATION_ADMIN
        resetAll()
        expect(userRepository.findByRoleAndOrganizationUuid(userRole, organization.uuid)).andReturn(listOf(user))
        replayAll()
        userService.findByRoleAndOrganizationUuid(userRole, organization.uuid).let {
            assertThat(it).isNotEmpty
            assertThat(it).containsOnly(user)
        }
        verifyAll()
    }

    @Test
    fun `test not found`() {
        val organization = organizationHelper.buildOrganization()
        val userRole = UserRole.ORGANIZATION_ADMIN
        resetAll()
        expect(userRepository.findByRoleAndOrganizationUuid(userRole, organization.uuid)).andReturn(emptyList())
        replayAll()
        userService.findByRoleAndOrganizationUuid(userRole, organization.uuid).let {
            assertThat(it).isEmpty()
        }
        verifyAll()
    }
}
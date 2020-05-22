package com.vntana.core.service.user.role.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.user.role.AbstractUserRoleServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 12:30 PM
 */
class UserRoleExistsByOrganizationAndUserAndRoleServiceUnitTest : AbstractUserRoleServiceUnitTest() {

    @Test
    fun `test when noting found`() {
        resetAll()
        val organizationUuid = uuid()
        val role = UserRole.ORGANIZATION_ADMIN
        expect(userRoleRepository.findAllByOrganizationUuid(organizationUuid)).andReturn(listOf())
        replayAll()
        val result = userRoleService.existsByOrganizationAndUserAndRole(organizationUuid, uuid(), role)
        assertThat(result).isFalse()
        verifyAll()
    }

    @Test
    fun `test admin role`() {
        resetAll()
        val organizationUuid = uuid()
        val user = userCommonTestHelper.buildUserWithOrganizationOwnerRole()
        val role = UserRole.ORGANIZATION_ADMIN
        val ownerRole = commonTestHelper.buildUserOrganizationOwnerRole(user = user)
        val adminRole1 = commonTestHelper.buildUserOrganizationAdminRole(user = user)
        val clientRole = commonTestHelper.buildUserClientOrganizationRole(user = user)
        expect(userRoleRepository.findAllByOrganizationUuid(organizationUuid)).andReturn(listOf(ownerRole, adminRole1, clientRole))
        replayAll()
        val result = userRoleService.existsByOrganizationAndUserAndRole(organizationUuid, user.uuid, role)
        assertThat(result).isTrue()
        verifyAll()
    }

    @Test
    fun `test admin role when different users exists in same organization`() {
        resetAll()
        val organizationUuid = uuid()
        val user = userCommonTestHelper.buildUserWithOrganizationOwnerRole()
        val role = UserRole.ORGANIZATION_ADMIN
        val ownerRole = commonTestHelper.buildUserOrganizationOwnerRole(user = user)
        val adminRole1 = commonTestHelper.buildUserOrganizationAdminRole(user = user)
        val adminRole2 = commonTestHelper.buildUserOrganizationAdminRole()
        val clientRole = commonTestHelper.buildUserClientOrganizationRole(user = user)
        expect(userRoleRepository.findAllByOrganizationUuid(organizationUuid)).andReturn(listOf(ownerRole, adminRole1, adminRole2, clientRole))
        replayAll()
        val result = userRoleService.existsByOrganizationAndUserAndRole(organizationUuid, user.uuid, role)
        assertThat(result).isTrue()
        verifyAll()
    }

    @Test
    fun `test when more then one role found`() {
        resetAll()
        val organizationUuid = uuid()
        val user = userCommonTestHelper.buildUserWithOrganizationOwnerRole()
        val role = UserRole.ORGANIZATION_ADMIN
        val adminRole1 = commonTestHelper.buildUserOrganizationAdminRole(user = user)
        val adminRole2 = commonTestHelper.buildUserOrganizationAdminRole(user = user)
        expect(userRoleRepository.findAllByOrganizationUuid(organizationUuid)).andReturn(listOf(adminRole1, adminRole2))
        replayAll()
        assertThatThrownBy { userRoleService.existsByOrganizationAndUserAndRole(organizationUuid, user.uuid, role) }
                .isExactlyInstanceOf(IllegalStateException::class.java)
        verifyAll()
    }

    @Test
    fun `test owner role`() {
        resetAll()
        val organizationUuid = uuid()
        val user = userCommonTestHelper.buildUserWithOrganizationOwnerRole()
        val role = UserRole.ORGANIZATION_OWNER
        val ownerRole = commonTestHelper.buildUserOrganizationOwnerRole(user = user)
        val adminRole1 = commonTestHelper.buildUserOrganizationAdminRole(user = user)
        val adminRole2 = commonTestHelper.buildUserOrganizationAdminRole(user = user)
        val clientRole = commonTestHelper.buildUserClientOrganizationRole(user = user)
        expect(userRoleRepository.findAllByOrganizationUuid(organizationUuid)).andReturn(listOf(ownerRole, adminRole1, adminRole2, clientRole))
        replayAll()
        val result = userRoleService.existsByOrganizationAndUserAndRole(organizationUuid, user.uuid, role)
        assertThat(result).isTrue()
        verifyAll()
    }
}
package com.vntana.core.service.user.role.impl

import com.vntana.core.service.user.role.AbstractUserRoleServiceUnitTest
import com.vntana.core.service.user.role.exception.UserOrganizationAdminRoleNotFoundException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 5/8/20
 * Time: 6:34 PM
 */
class UserRoleRevokeOrganizationAdminRoleServiceUnitTest : AbstractUserRoleServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { userRoleService.revokeOrganizationAdminRole(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userRoleService.revokeOrganizationAdminRole(commonTestHelper.buildUserRevokeOrganizationAdminRoleDto(userUuid = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userRoleService.revokeOrganizationAdminRole(commonTestHelper.buildUserRevokeOrganizationAdminRoleDto(userUuid = "")) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userRoleService.revokeOrganizationAdminRole(commonTestHelper.buildUserRevokeOrganizationAdminRoleDto(organizationUuid = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userRoleService.revokeOrganizationAdminRole(commonTestHelper.buildUserRevokeOrganizationAdminRoleDto(organizationUuid = "")) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val dto = commonTestHelper.buildUserRevokeOrganizationAdminRoleDto()
        expect(userRoleRepository.findAdminRoleByUserAndOrganization(dto.userUuid, dto.organizationUuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { userRoleService.revokeOrganizationAdminRole(dto) }
                .isExactlyInstanceOf(UserOrganizationAdminRoleNotFoundException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val role = commonTestHelper.buildUserOrganizationAdminRole()
        val dto = commonTestHelper.buildUserRevokeOrganizationAdminRoleDto()
        expect(userRoleRepository.findAdminRoleByUserAndOrganization(dto.userUuid, dto.organizationUuid)).andReturn(Optional.of(role))
        expect(userRoleRepository.delete(role))
        replayAll()
        userRoleService.revokeOrganizationAdminRole(dto)
        verifyAll()
    }
}
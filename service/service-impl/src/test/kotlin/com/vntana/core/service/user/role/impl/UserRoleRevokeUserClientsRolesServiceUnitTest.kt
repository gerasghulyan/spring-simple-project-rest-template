package com.vntana.core.service.user.role.impl

import com.vntana.core.service.user.role.AbstractUserRoleServiceUnitTest
import com.vntana.core.service.user.role.exception.UserClientsIncorrectRolesRevokeException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 12/3/2020
 * Time: 12:46 PM
 */
class UserRoleRevokeUserClientsRolesServiceUnitTest : AbstractUserRoleServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertIllegalArgumentException { userRoleService.revokeUserClientsRoles(null) }
        assertIllegalArgumentException { userRoleService.revokeUserClientsRoles(commonTestHelper.buildUserRevokeClientsRolesDto(userUuid = null)) }
        assertIllegalArgumentException { userRoleService.revokeUserClientsRoles(commonTestHelper.buildUserRevokeClientsRolesDto(userUuid = emptyString())) }
        assertIllegalArgumentException { userRoleService.revokeUserClientsRoles(commonTestHelper.buildUserRevokeClientsRolesDto(clientUuids = null)) }
        assertIllegalArgumentException { userRoleService.revokeUserClientsRoles(commonTestHelper.buildUserRevokeClientsRolesDto(clientUuids = listOf())) }
        verifyAll()
    }

    @Test
    fun `test incorrect clients`() {
        val dto = commonTestHelper.buildUserRevokeClientsRolesDto()
        resetAll()
        expect(userRoleRepository.deleteAllForUserAndClientOrganizations(dto.userUuid, dto.clientUuids)).andReturn(1)
        replayAll()
        assertThatThrownBy { userRoleService.revokeUserClientsRoles(dto) }.isExactlyInstanceOf(UserClientsIncorrectRolesRevokeException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        val dto = commonTestHelper.buildUserRevokeClientsRolesDto()
        resetAll()
        expect(userRoleRepository.deleteAllForUserAndClientOrganizations(dto.userUuid, dto.clientUuids)).andReturn(dto.clientUuids.size)
        replayAll()
        userRoleService.revokeUserClientsRoles(dto)
        verifyAll()
    }
}
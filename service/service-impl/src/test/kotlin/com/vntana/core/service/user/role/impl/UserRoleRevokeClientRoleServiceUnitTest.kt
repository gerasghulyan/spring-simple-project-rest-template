package com.vntana.core.service.user.role.impl

import com.vntana.core.service.user.role.AbstractUserRoleServiceUnitTest
import com.vntana.core.service.user.role.dto.UserClientRole
import com.vntana.core.service.user.role.exception.UserClientRoleNotFoundException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 05.11.2020
 * Time: 11:57
 */
class UserRoleRevokeClientRoleServiceUnitTest : AbstractUserRoleServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertIllegalArgumentException { userRoleService.revokeClientRole(null) }
        assertIllegalArgumentException { userRoleService.revokeClientRole(commonTestHelper.buildUserRevokeClientRoleDto(userUuid = null)) }
        assertIllegalArgumentException { userRoleService.revokeClientRole(commonTestHelper.buildUserRevokeClientRoleDto(userUuid = emptyString())) }
        assertIllegalArgumentException { userRoleService.revokeClientRole(commonTestHelper.buildUserRevokeClientRoleDto(clientOrganizationUuid = null)) }
        assertIllegalArgumentException { userRoleService.revokeClientRole(commonTestHelper.buildUserRevokeClientRoleDto(clientOrganizationUuid = emptyString())) }
        assertIllegalArgumentException { userRoleService.revokeClientRole(commonTestHelper.buildUserRevokeClientRoleDto(clientRole = null)) }
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val dto = commonTestHelper.buildUserRevokeClientRoleDto()
        expect(userRoleRepository.findClientAdminRoleByUserAndClientOrganization(dto.userUuid, dto.clientOrganizationUuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { userRoleService.revokeClientRole(dto) }
                .isExactlyInstanceOf(UserClientRoleNotFoundException::class.java)
        verifyAll()
    }

    @Test
    fun `test client admin`() {
        resetAll()
        val user = userCommonTestHelper.buildUser()
        val clientAdminRole = commonTestHelper.buildUserClientAdminRole()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val dto = commonTestHelper.buildUserRevokeClientRoleDto(
                userUuid = user.uuid,
                clientOrganizationUuid = clientOrganization.uuid,
                clientRole = UserClientRole.CLIENT_ADMIN
        )
        expect(userRoleRepository.findClientAdminRoleByUserAndClientOrganization(dto.userUuid, dto.clientOrganizationUuid)).andReturn(Optional.of(clientAdminRole))
        expect(userRoleRepository.delete(clientAdminRole))
        replayAll()
        userRoleService.revokeClientRole(dto)
        verifyAll()
    }
    
    @Test
    fun `test client content manager`() {
        resetAll()
        val user = userCommonTestHelper.buildUser()
        val clientContentManagerRole = commonTestHelper.buildUserClientContentManagerRole()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val dto = commonTestHelper.buildUserRevokeClientRoleDto(
                userUuid = user.uuid,
                clientOrganizationUuid = clientOrganization.uuid,
                clientRole = UserClientRole.CLIENT_CONTENT_MANAGER
        )
        expect(userRoleRepository.findClientContentManagerRoleByUserAndClientOrganization(dto.userUuid, dto.clientOrganizationUuid)).andReturn(Optional.of(clientContentManagerRole))
        expect(userRoleRepository.delete(clientContentManagerRole))
        replayAll()
        userRoleService.revokeClientRole(dto)
        verifyAll()
    }
    
    @Test
    fun `test client viewer`() {
        resetAll()
        val user = userCommonTestHelper.buildUser()
        val role = commonTestHelper.buildUserClientViewerRole()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val dto = commonTestHelper.buildUserRevokeClientRoleDto(
                userUuid = user.uuid,
                clientOrganizationUuid = clientOrganization.uuid,
                clientRole = UserClientRole.CLIENT_VIEWER
        )
        expect(userRoleRepository.findClientViewerRoleByUserAndClientOrganization(dto.userUuid, dto.clientOrganizationUuid)).andReturn(Optional.of(role))
        expect(userRoleRepository.delete(role))
        replayAll()
        userRoleService.revokeClientRole(dto)
        verifyAll()
    }
}
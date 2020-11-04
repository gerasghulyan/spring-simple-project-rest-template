package com.vntana.core.service.user.role.impl

import com.vntana.core.domain.user.AbstractUserRole
import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.user.role.AbstractUserRoleServiceUnitTest
import com.vntana.core.service.user.role.dto.UserClientRole
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.*
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 03.11.2020
 * Time: 18:28
 */
class UserRoleGrantClientRoleServiceUnitTest : AbstractUserRoleServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertIllegalArgumentException { userRoleService.grantClientRole(null) }
        assertIllegalArgumentException { userRoleService.grantClientRole(commonTestHelper.buildUserGrantClientRoleDto(userUuid = null)) }
        assertIllegalArgumentException { userRoleService.grantClientRole(commonTestHelper.buildUserGrantClientRoleDto(userUuid = emptyString())) }
        assertIllegalArgumentException { userRoleService.grantClientRole(commonTestHelper.buildUserGrantClientRoleDto(clientOrganizationUuid = null)) }
        assertIllegalArgumentException { userRoleService.grantClientRole(commonTestHelper.buildUserGrantClientRoleDto(clientOrganizationUuid = emptyString())) }
        assertIllegalArgumentException { userRoleService.grantClientRole(commonTestHelper.buildUserGrantClientRoleDto(clientRole = null)) }
        verifyAll()
    }

    @Test
    fun `test client admin`() {
        resetAll()
        val user = userCommonTestHelper.buildUserWithOrganizationOwnerRole()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val dto = commonTestHelper.buildUserGrantClientRoleDto(userUuid = user.uuid, clientOrganizationUuid = clientOrganization.uuid, clientRole = UserClientRole.CLIENT_ADMIN)
        expect(userService.getByUuid(dto.userUuid)).andReturn(user)
        expect(clientOrganizationService.getByUuid(dto.clientOrganizationUuid)).andReturn(clientOrganization)
        expect(userRoleRepository.save(isA(AbstractUserRole::class.java))).andAnswer { getCurrentArguments()[0] as AbstractUserRole }
        replayAll()
        userRoleService.grantClientRole(dto).let {
            assertThat(it.user).isEqualTo(user)
            assertThat(it.userRole).isEqualTo(UserRole.CLIENT_ADMIN)
        }
        verifyAll()
    }
    
    @Test
    fun `test client content manager`() {
        resetAll()
        val user = userCommonTestHelper.buildUserWithOrganizationOwnerRole()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val dto = commonTestHelper.buildUserGrantClientRoleDto(userUuid = user.uuid, clientOrganizationUuid = clientOrganization.uuid, clientRole = UserClientRole.CLIENT_CONTENT_MANAGER)
        expect(userService.getByUuid(dto.userUuid)).andReturn(user)
        expect(clientOrganizationService.getByUuid(dto.clientOrganizationUuid)).andReturn(clientOrganization)
        expect(userRoleRepository.save(isA(AbstractUserRole::class.java))).andAnswer { getCurrentArguments()[0] as AbstractUserRole }
        replayAll()
        userRoleService.grantClientRole(dto).let {
            assertThat(it.user).isEqualTo(user)
            assertThat(it.userRole).isEqualTo(UserRole.CLIENT_CONTENT_MANAGER)
        }
        verifyAll()
    }
    
    @Test
    fun `test client viewer`() {
        resetAll()
        val user = userCommonTestHelper.buildUserWithOrganizationOwnerRole()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val dto = commonTestHelper.buildUserGrantClientRoleDto(userUuid = user.uuid, clientOrganizationUuid = clientOrganization.uuid, clientRole = UserClientRole.CLIENT_VIEWER)
        expect(userService.getByUuid(dto.userUuid)).andReturn(user)
        expect(clientOrganizationService.getByUuid(dto.clientOrganizationUuid)).andReturn(clientOrganization)
        expect(userRoleRepository.save(isA(AbstractUserRole::class.java))).andAnswer { getCurrentArguments()[0] as AbstractUserRole }
        replayAll()
        userRoleService.grantClientRole(dto).let {
            assertThat(it.user).isEqualTo(user)
            assertThat(it.userRole).isEqualTo(UserRole.CLIENT_VIEWER)
        }
        verifyAll()
    }
}
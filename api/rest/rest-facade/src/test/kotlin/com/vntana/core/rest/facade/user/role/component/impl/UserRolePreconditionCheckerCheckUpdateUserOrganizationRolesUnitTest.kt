package com.vntana.core.rest.facade.user.role.component.impl

import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel
import com.vntana.core.rest.facade.user.role.component.AbstractUserRoleFacadePreconditionCheckerComponentUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 15.12.2020
 * Time: 16:55
 */
class UserRolePreconditionCheckerCheckUpdateUserOrganizationRolesUnitTest : AbstractUserRoleFacadePreconditionCheckerComponentUnitTest() {

    @Test
    fun `test when organization not found`() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationRoleRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkUpdateUserOrganizationRoles(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.ORGANIZATION_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when user not found`() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationRoleRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.requestedUserUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkUpdateUserOrganizationRoles(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.USER_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when user has organization admin role`() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationRoleRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.requestedUserUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.requestedUserUuid)).andReturn(Optional.of(commonTestHelper.buildUserOrganizationAdminRole()))
        replayAll()
        preconditionChecker.checkUpdateUserOrganizationRoles(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.REQUESTED_USER_ALREADY_HAS_ORGANIZATION_ADMIN_ROLE)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
        }
        verifyAll()
    }
    
    @Test
    fun `test when user has organization owner role`() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationRoleRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.requestedUserUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.requestedUserUuid)).andReturn(Optional.of(commonTestHelper.buildUserOrganizationOwnerRole()))
        replayAll()
        preconditionChecker.checkUpdateUserOrganizationRoles(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.REQUESTED_USER_IS_ORGANIZATION_OWNER)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
        }
        verifyAll()
    }
    
    @Test
    fun `test when authorized user does not have organization role`() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationRoleRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.requestedUserUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.requestedUserUuid)).andReturn(Optional.empty())
        expect(userRolesPermissionsCheckerComponent.isPermittedToUpdateOrganizationRole(request)).andReturn(false)
        replayAll()
        preconditionChecker.checkUpdateUserOrganizationRoles(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.INCORRECT_PERMISSION_GRANT_ORGANIZATION_ROLE)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_FORBIDDEN)
        }
        verifyAll()
    }
    
    @Test
    fun `test when owner grants admin`() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationRoleRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.requestedUserUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.requestedUserUuid)).andReturn(Optional.empty())
        expect(userRolesPermissionsCheckerComponent.isPermittedToUpdateOrganizationRole(request)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkUpdateUserOrganizationRoles(request).isPresent).isEqualTo(false)
        verifyAll()
    }
    
    @Test
    fun `test when super admin grants admin`() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationRoleRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.requestedUserUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.requestedUserUuid)).andReturn(Optional.empty())
        expect(userRolesPermissionsCheckerComponent.isPermittedToUpdateOrganizationRole(request)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkUpdateUserOrganizationRoles(request).isPresent).isEqualTo(false)
        verifyAll()
    }
    
    @Test
    fun `test when admin grants admin`() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationRoleRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.requestedUserUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.requestedUserUuid)).andReturn(Optional.empty())
        expect(userRolesPermissionsCheckerComponent.isPermittedToUpdateOrganizationRole(request)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkUpdateUserOrganizationRoles(request).isPresent).isEqualTo(false)
        verifyAll()
    }
}
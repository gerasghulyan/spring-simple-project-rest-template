package com.vntana.core.rest.facade.user.role.component.impl

import com.vntana.core.model.auth.response.UserRoleModel
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
        expect(userService.existsByUuid(request.userUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkUpdateUserOrganizationRoles(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.USER_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when user has organization owner role`() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationRoleRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(Optional.of(commonTestHelper.buildUserOrganizationOwnerRole()))
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
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(Optional.empty())
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.authorizedUserUuid)).andReturn(Optional.empty())
        expect(userService.findByUuid(request.authorizedUserUuid)).andReturn(Optional.empty())
        replayAll()
        preconditionChecker.checkUpdateUserOrganizationRoles(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.USER_ORGANIZATION_ROLE_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_FORBIDDEN)
        }
        verifyAll()
    }
    
    @Test
    fun `test when owner grants admin`() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationRoleRequest()
        val granterOrganizationOwnerRole = commonTestHelper.buildUserOrganizationOwnerRole()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(Optional.empty())
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.authorizedUserUuid)).andReturn(Optional.of(granterOrganizationOwnerRole))
        expect(userService.findByUuid(request.authorizedUserUuid)).andReturn(Optional.empty())
        expect(userRolesPermissionsCheckerComponent.isPermittedToGrant(UserRoleModel.valueOf(granterOrganizationOwnerRole.userRole.name), request.userRoleModel)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkUpdateUserOrganizationRoles(request).isPresent).isEqualTo(false)
        verifyAll()
    }
    
    @Test
    fun `test when super admin grants admin`() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationRoleRequest()
        val superAdminUser = userCommonTestHelper.buildUser()
        superAdminUser.grantSuperAdminRole()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(Optional.empty())
        expect(userService.findByUuid(request.authorizedUserUuid)).andReturn(Optional.of(superAdminUser))
        replayAll()
        assertThat(preconditionChecker.checkUpdateUserOrganizationRoles(request).isPresent).isEqualTo(false)
        verifyAll()
    }
    
    @Test
    fun `test when admin grants admin`() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationRoleRequest()
        val granterOrganizationOwnerRole = commonTestHelper.buildUserOrganizationAdminRole()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(Optional.empty())
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.authorizedUserUuid)).andReturn(Optional.of(granterOrganizationOwnerRole))
        expect(userService.findByUuid(request.authorizedUserUuid)).andReturn(Optional.empty())
        expect(userRolesPermissionsCheckerComponent.isPermittedToGrant(UserRoleModel.valueOf(granterOrganizationOwnerRole.userRole.name), request.userRoleModel)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkUpdateUserOrganizationRoles(request).isPresent).isEqualTo(false)
        verifyAll()
    }
}
package com.vntana.core.rest.facade.invitation.user.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 23.12.2020
 * Time: 18:44
 */
class UserRolesPermissionCheckerCheckUserRolesPermissionCheckerIsPermittedToUpdateOrganizationRoleUnitTest : AbstractUserRolesPermissionsCheckerComponentUnitTest() {

    @Test
    fun `test when SUPPER ADMIN updates`() {
        resetAll()
        val request = userRoleRestTestHelper.buildUserUpdateOrganizationRoleRequest()
        val superAdminUser = userCommonTestHelper.buildUser()
        superAdminUser.grantSuperAdminRole()
        expect(userService.findByUuid(request.uuid)).andReturn(Optional.of(superAdminUser))
        replayAll()
        assertThat(checkerComponent.isPermittedToUpdateOrganizationRole(request)).isTrue()
        verifyAll()
    }

    @Test
    fun `test when organization user not found`() {
        resetAll()
        val request = userRoleRestTestHelper.buildUserUpdateOrganizationRoleRequest()
        expect(userService.findByUuid(request.uuid)).andReturn(Optional.empty())
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.uuid)).andReturn(Optional.empty())
        replayAll()
        assertThat(checkerComponent.isPermittedToUpdateOrganizationRole(request)).isFalse()
        verifyAll()
    }
    
    @Test
    fun `test when ORGANIZATION_OWNER updates`() {
        resetAll()
        val ownerRole = userRoleCommonTestHelper.buildUserOrganizationOwnerRole()
        val request = userRoleRestTestHelper.buildUserUpdateOrganizationRoleRequest()
        expect(userService.findByUuid(request.uuid)).andReturn(Optional.empty())
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.uuid)).andReturn(Optional.of(ownerRole))
        replayAll()
        assertThat(checkerComponent.isPermittedToUpdateOrganizationRole(request)).isTrue()
        verifyAll()
    }
    
    @Test
    fun `test when ORGANIZATION_ADMIN updates`() {
        resetAll()
        val request = userRoleRestTestHelper.buildUserUpdateOrganizationRoleRequest()
        val adminRole = userRoleCommonTestHelper.buildUserOrganizationAdminRole()
        expect(userService.findByUuid(request.uuid)).andReturn(Optional.empty())
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.uuid)).andReturn(Optional.of(adminRole))
        replayAll()
        assertThat(checkerComponent.isPermittedToUpdateOrganizationRole(request)).isTrue()
        verifyAll()
    }
}
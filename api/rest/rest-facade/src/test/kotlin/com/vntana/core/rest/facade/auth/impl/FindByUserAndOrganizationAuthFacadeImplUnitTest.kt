package com.vntana.core.rest.facade.auth.impl

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.security.request.FindUserByUuidAndOrganizationRequest
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.auth.AbstractAuthFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan
 * Date: 08.11.19
 * Time: 11:42
 */
class FindByUserAndOrganizationAuthFacadeImplUnitTest : AbstractAuthFacadeUnitTest() {

    @Test
    fun `test when user not found`() {
        resetAll()
        val userUuid = uuid()
        expect(userService.findByUuid(userUuid)).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(
                authFacade.findByUserAndOrganization(FindUserByUuidAndOrganizationRequest(userUuid, uuid())),
                UserErrorResponseModel.NOT_FOUND_FOR_ROLE
        )
        verifyAll()
    }

    @Test
    fun `test user is super admin`() {
        resetAll()
        val user = userHelper.buildUserWithOrganizationOwnerRole()
        user.grantSuperAdminRole()
        userRoleCommonTestHelper.buildUserOrganizationSuperAdminRole()
        val userUuid = user.uuid
        val request = FindUserByUuidAndOrganizationRequest(userUuid, uuid())
        expect(userService.findByUuid(userUuid)).andReturn(Optional.of(user))
        replayAll()
        authFacade.findByUserAndOrganization(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().uuid).isEqualTo(request.uuid)
            assertThat(it.response().organizationUuid).isEqualTo(request.organizationUuid)
            assertThat(it.response().username).isEqualTo(user.email)
            assertThat(it.response().superAdmin).isTrue()
        }
        verifyAll()
    }

    @Test
    fun `test user is admin and also super admin`() {
        resetAll()
        val user = userHelper.buildUserWithOrganizationOwnerRole()
        user.grantSuperAdminRole()
        userRoleCommonTestHelper.buildUserOrganizationAdminRole()
        val userUuid = user.uuid
        val request = FindUserByUuidAndOrganizationRequest(userUuid, uuid())
        expect(userService.findByUuid(userUuid)).andReturn(Optional.of(user))
        replayAll()
        authFacade.findByUserAndOrganization(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().uuid).isEqualTo(request.uuid)
            assertThat(it.response().organizationUuid).isEqualTo(request.organizationUuid)
            assertThat(it.response().username).isEqualTo(user.email)
            assertThat(it.response().superAdmin).isTrue()
        }
        verifyAll()
    }

    @Test
    fun `test user has no any role`() {
        resetAll()
        val user = userHelper.buildUserWithOrganizationOwnerRole()
        val adminRole = userRoleCommonTestHelper.buildUserOrganizationAdminRole()
        val userUuid = user.uuid
        val request = FindUserByUuidAndOrganizationRequest(userUuid, uuid())
        expect(userService.findByUuid(userUuid)).andReturn(Optional.of(user))
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.uuid)).andReturn(Optional.of(adminRole))
        replayAll()
        authFacade.findByUserAndOrganization(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().userRole).isEqualTo(UserRoleModel.ORGANIZATION_ADMIN)
            assertThat(it.response().uuid).isEqualTo(request.uuid)
            assertThat(it.response().organizationUuid).isEqualTo(request.organizationUuid)
            assertThat(it.response().username).isEqualTo(user.email)
            assertThat(it.response().superAdmin).isFalse()
        }
        verifyAll()
    }


}
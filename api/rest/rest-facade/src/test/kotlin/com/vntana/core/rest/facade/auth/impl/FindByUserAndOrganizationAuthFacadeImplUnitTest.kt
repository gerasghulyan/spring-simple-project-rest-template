package com.vntana.core.rest.facade.auth.impl

import com.vntana.core.domain.user.UserRole
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.security.request.FindUserByUuidAndOrganizationRequest
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.auth.AbstractAuthFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.anyString
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
    fun `test when no role found by organization`() {
        resetAll()
        val user = userHelper.buildUser()
        expect(userService.findByUuid(anyString())).andReturn(Optional.of(user))
        expect(userRoleService.findByOrganizationAndUser(anyString(), anyString())).andReturn(Optional.empty())
        expect(userRoleService.existsClientOrganizationRoleByOrganizationAndUser(anyString(), anyString())).andReturn(false)
        replayAll()
        assertBasicErrorResultResponse(
                authFacade.findByUserAndOrganization(FindUserByUuidAndOrganizationRequest(user.uuid, uuid())),
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
    fun `test user has client organization role but no organization role`() {
        resetAll()
        val user = userHelper.buildUser()
        val request = FindUserByUuidAndOrganizationRequest(user.uuid, uuid())
        expect(userService.findByUuid(anyString())).andReturn(Optional.of(user))
        expect(userRoleService.findByOrganizationAndUser(anyString(), anyString())).andReturn(Optional.empty())
        expect(userRoleService.existsClientOrganizationRoleByOrganizationAndUser(anyString(), anyString())).andReturn(true)
        replayAll()
        authFacade.findByUserAndOrganization(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().uuid).isEqualTo(request.uuid)
            assertThat(it.response().organizationUuid).isEqualTo(request.organizationUuid)
            assertThat(it.response().username).isEqualTo(user.email)
            assertThat(it.response().userRole).isEqualTo(UserRoleModel.ORGANIZATION_CLIENTS_VIEWER)
            assertThat(it.response().superAdmin).isFalse()
        }
        verifyAll()
    }
}
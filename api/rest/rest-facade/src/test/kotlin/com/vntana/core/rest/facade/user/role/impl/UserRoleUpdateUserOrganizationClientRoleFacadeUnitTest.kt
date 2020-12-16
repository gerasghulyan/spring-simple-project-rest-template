package com.vntana.core.rest.facade.user.role.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.domain.user.UserRole
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel
import com.vntana.core.rest.facade.user.role.AbstractUserRoleServiceFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 15.12.2020
 * Time: 21:21
 */
class UserRoleUpdateUserOrganizationClientRoleFacadeUnitTest: AbstractUserRoleServiceFacadeUnitTest() {

    @Test
    fun `test when precondition failed`() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationClientRoleRequest()
        val error = SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, UserRoleErrorResponseModel.USER_NOT_FOUND)
        expect(preconditionChecker.checkUpdateUserOrganizationClientsRoles(request)).andReturn(error)
        replayAll()
        assertBasicErrorResultResponse(userRoleServiceFacade.updateUserOrganizationClientsRoles(request), error.error)
        verifyAll()
    }

    @Test
    fun `test grant client roles to organization admin`() {
        resetAll()
        val updateClientRoleRequest = restTestHelper.buildUpdateClientRoleRequest(userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)
        val request = restTestHelper.buildUserUpdateOrganizationClientRoleRequest(updateClientRoles = listOf(updateClientRoleRequest))
        val revokeClientRole = restTestHelper.buildUpdateClientRoleRequest()
        val grantClientRole = restTestHelper.buildUpdateClientRoleRequest(userRoleModel = updateClientRoleRequest.clientRole)
        val revokeClientRequest = restTestHelper.buildUserRoleRevokeClientRequest(userUuid = request.requestedUserUuid, clientUuid = revokeClientRole.clientUuid, userRole = revokeClientRole.clientRole)
        val revokeClientRoleDto = commonTestHelper.buildUserRevokeClientRoleDto(userUuid = revokeClientRequest.userUuid, clientOrganizationUuid = revokeClientRequest.clientUuid, clientRole = UserRole.valueOf(revokeClientRequest.userRole.name))
        val grantClientRequest = restTestHelper.buildUserRoleGrantClientRequest(userUuid = request.requestedUserUuid, clientUuid = grantClientRole.clientUuid, userRole = grantClientRole.clientRole)
        val grantClientRoleDto = commonTestHelper.buildUserGrantClientRoleDto(userUuid = grantClientRequest.userUuid, clientRole = UserRole.valueOf(grantClientRequest.userRole.name), clientOrganizationUuid = grantClientRequest.clientUuid)
        val clintContentManagerRole = commonTestHelper.buildUserClientContentManagerRole()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val adminRole = commonTestHelper.buildUserOrganizationAdminRole()
        val dto = commonTestHelper.buildUserRevokeOrganizationAdminRoleDto(
                userUuid = request.requestedUserUuid,
                organizationUuid = request.organizationUuid
        )
        expect(preconditionChecker.checkUpdateUserOrganizationClientsRoles(request)).andReturn(SingleErrorWithStatus.empty())
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.requestedUserUuid)).andReturn(Optional.of(adminRole))
        expect(userRoleService.revokeOrganizationAdminRole(dto))
        expect(userRoleHelperComponent.fetchRevokeRolesFromUpdateRolesRequest(request)).andReturn(listOf(revokeClientRole))
        expect(preconditionChecker.checkRevokeClientRole(revokeClientRequest)).andReturn(SingleErrorWithStatus.empty())
        expect(userRoleService.revokeClientRole(revokeClientRoleDto))
        expect(tokenAuthenticationService.expireAllByUser(revokeClientRequest.userUuid))
        expect(userRoleHelperComponent.fetchGrantRolesFromUpdateRolesRequest(request)).andReturn(listOf(grantClientRole))
        expect(preconditionChecker.checkGrantClientRole(grantClientRequest)).andReturn(SingleErrorWithStatus.empty())
        expect(organizationClientService.getByUuid(grantClientRequest.clientUuid)).andReturn(clientOrganization)
        expect(userRoleService.findByOrganizationAndUser(clientOrganization.organization.uuid, request.requestedUserUuid)).andReturn(Optional.empty())
        expect(userRoleService.grantClientRole(grantClientRoleDto)).andReturn(clintContentManagerRole)
        replayAll()
        userRoleServiceFacade.updateUserOrganizationClientsRoles(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().userUuid).isEqualTo(request.requestedUserUuid)
        }
        verifyAll()
    }
    
}
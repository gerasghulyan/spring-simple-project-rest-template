package com.vntana.core.rest.resource.user.role.impl

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel
import com.vntana.core.rest.resource.user.role.AbstractUserRoleWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Manuk Gharslyan.
 * Date: 12/9/2020
 * Time: 6:22 PM
 */
class UserRoleRevokeUserOrganizationClientsRolesWebTest : AbstractUserRoleWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.revokeUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserRoleRevokeOrganizationClientsRequest(organizationUuid = null)),
                UserRoleErrorResponseModel.MISSING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.revokeUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserRoleRevokeOrganizationClientsRequest(organizationUuid = emptyString())),
                UserRoleErrorResponseModel.MISSING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.revokeUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserRoleRevokeOrganizationClientsRequest(userUuid = null)),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.revokeUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserRoleRevokeOrganizationClientsRequest(userUuid = emptyString())),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.revokeUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserRoleRevokeOrganizationClientsRequest(revocableUserUuid = null)),
                UserRoleErrorResponseModel.MISSING_REVOCABLE_USER_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.revokeUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserRoleRevokeOrganizationClientsRequest(revocableUserUuid = emptyString())),
                UserRoleErrorResponseModel.MISSING_REVOCABLE_USER_UUID
        )
    }

    @Test
    fun `test when organization not found`() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val result = userRoleResourceClient.revokeUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserRoleRevokeOrganizationClientsRequest(userUuid = userUuid))
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND, result, UserRoleErrorResponseModel.ORGANIZATION_NOT_FOUND)
    }

    @Test
    fun `test when revoker user not found`() {
        val organizationUuid = userResourceTestHelper.persistUser().response().organizationUuid
        val result = userRoleResourceClient.revokeUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserRoleRevokeOrganizationClientsRequest(organizationUuid = organizationUuid))
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND, result, UserRoleErrorResponseModel.USER_NOT_FOUND)
    }

    @Test
    fun `test when revoker does not have organization role`() {
        val userResponse = userResourceTestHelper.persistUser().response()
        val newOrganizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val newClientUuid = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = newOrganizationUuid).response().uuid
        userRoleResourceTestHelper.grantUserClientRole(userUuid = userResponse.uuid, clientUuid = newClientUuid)
        val result = userRoleResourceClient.revokeUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserRoleRevokeOrganizationClientsRequest(userUuid = userResponse.uuid, organizationUuid = newOrganizationUuid))
        assertBasicErrorResultResponse(HttpStatus.CONFLICT, result, UserRoleErrorResponseModel.INCORRECT_REVOKER_USER_ROLE)
    }

    @Test
    fun `test when revocable user not found`() {
        val userResponse = userResourceTestHelper.persistUser().response()
        val result = userRoleResourceClient.revokeUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserRoleRevokeOrganizationClientsRequest(organizationUuid = userResponse.organizationUuid, userUuid = userResponse.uuid))
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND, result, UserRoleErrorResponseModel.REVOCABLE_USER_NOT_FOUND)
    }

    @Test
    fun `test when user has organization role`() {
        val userResponse = userResourceTestHelper.persistUser().response()
        val revocableUser = userResourceTestHelper.persistUser().response()
        userRoleResourceTestHelper.grantUserOrganizationAdminRole(userUuid = revocableUser.uuid, organizationUuid = userResponse.organizationUuid)
        val result = userRoleResourceClient.revokeUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserRoleRevokeOrganizationClientsRequest(
                organizationUuid = userResponse.organizationUuid,
                userUuid = userResponse.uuid,
                revocableUserUuid = revocableUser.uuid
        ))
        assertBasicErrorResultResponse(HttpStatus.CONFLICT, result, UserRoleErrorResponseModel.REVOCABLE_USER_HAS_ORGANIZATION_ROLE)
    }

    @Test
    fun `test when user does not have client role in organization`() {
        val userResponse = userResourceTestHelper.persistUser().response()
        val revocableUserResponse = userResourceTestHelper.persistUser().response()
        val clientUuid = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = userResponse.organizationUuid).response().uuid
        userRoleResourceTestHelper.grantUserClientRole(userUuid = revocableUserResponse.uuid, clientUuid = clientUuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)
        userRoleResourceTestHelper.revokeClientRole(userUuid = revocableUserResponse.uuid, clientUuid = clientUuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)
        val result = userRoleResourceClient.revokeUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserRoleRevokeOrganizationClientsRequest(
                organizationUuid = userResponse.organizationUuid,
                userUuid = userResponse.uuid,
                revocableUserUuid = revocableUserResponse.uuid
        ))
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND, result, UserRoleErrorResponseModel.REVOCABLE_USER_DOES_NOT_HAVE_CLIENT_ROLE)
    }

    @Test
    fun test() {
        val userResponse = userResourceTestHelper.persistUser().response()
        val revocableUserResponse = userResourceTestHelper.persistUser().response()
        val clientUuid1 = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = userResponse.organizationUuid).response().uuid
        val clientUuid2 = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = userResponse.organizationUuid).response().uuid
        userRoleResourceTestHelper.grantUserClientRole(userUuid = revocableUserResponse.uuid, clientUuid = clientUuid1, userRole = UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)
        userRoleResourceTestHelper.grantUserClientRole(userUuid = revocableUserResponse.uuid, clientUuid = clientUuid2, userRole = UserRoleModel.CLIENT_ORGANIZATION_VIEWER)
        userRoleResourceClient.revokeUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserRoleRevokeOrganizationClientsRequest(
                organizationUuid = userResponse.organizationUuid,
                userUuid = userResponse.uuid,
                revocableUserUuid = revocableUserResponse.uuid
        )).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body?.response()?.userUuid).isEqualTo(revocableUserResponse.uuid)
        }
    }
}
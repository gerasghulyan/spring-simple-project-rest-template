package com.vntana.core.rest.resource.user.role.impl

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel
import com.vntana.core.rest.resource.user.role.AbstractUserRoleWebTest
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Arman Gevorgyan.
 * Date: 5/27/20
 * Time: 2:32 PM
 */
class UserRoleGrantClientOrganizationRoleWebTest : AbstractUserRoleWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.grantUserClientRole(userRoleResourceTestHelper.buildUserRoleGrantClientRequest(userUuid = null)),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.grantUserClientRole(userRoleResourceTestHelper.buildUserRoleGrantClientRequest(userUuid = emptyString())),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.grantUserClientRole(userRoleResourceTestHelper.buildUserRoleGrantClientRequest(clientUuid = null)),
                UserRoleErrorResponseModel.MISSING_CLIENT_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.grantUserClientRole(userRoleResourceTestHelper.buildUserRoleGrantClientRequest(clientUuid = emptyString())),
                UserRoleErrorResponseModel.MISSING_CLIENT_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.grantUserClientRole(userRoleResourceTestHelper.buildUserRoleGrantClientRequest(userRole = null)),
                UserRoleErrorResponseModel.MISSING_CLIENT_ROLE
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.grantUserClientRole(userRoleResourceTestHelper.buildUserRoleGrantClientRequest(userRole = UserRoleModel.ORGANIZATION_ADMIN)),
                UserRoleErrorResponseModel.REQUEST_ROLE_IS_NOT_CLIENT_RELATED
        )
    }

    @Test
    fun `test when client does not exist`() {
        assertBasicErrorResultResponse(
                HttpStatus.NOT_FOUND,
                userRoleResourceClient.grantUserClientRole(userRoleResourceTestHelper.buildUserRoleGrantClientRequest()),
                UserRoleErrorResponseModel.CLIENT_ORGANIZATION_NOT_FOUND
        )
    }

    @Test
    fun `test when user does not exist`() {
        val clientUuid = clientOrganizationResourceTestHelper.persistClientOrganization().response().uuid
        assertBasicErrorResultResponse(
                HttpStatus.NOT_FOUND,
                userRoleResourceClient.grantUserClientRole(userRoleResourceTestHelper.buildUserRoleGrantClientRequest(clientUuid = clientUuid)),
                UserRoleErrorResponseModel.USER_NOT_FOUND
        )
    }

    @Test
    fun `test when user already has a role in current organization`() {
        val response = userResourceTestHelper.persistUser().response()
        val userUuid = response.uuid
        val clientUuid = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = response.organizationUuid).response().uuid
        assertBasicErrorResultResponse(
                HttpStatus.CONFLICT,
                userRoleResourceClient.grantUserClientRole(userRoleResourceTestHelper.buildUserRoleGrantClientRequest(clientUuid = clientUuid, userUuid = userUuid)),
                UserRoleErrorResponseModel.REQUESTED_ROLE_ALREADY_GRANTED
        )
    }

    @Test
    fun `test when requested role client already granted`() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val clientUuid = clientOrganizationResourceTestHelper.persistClientOrganization().response().uuid
        userRoleResourceTestHelper.grantUserClientRole(userUuid = userUuid, clientUuid = clientUuid)
        assertBasicErrorResultResponse(
                HttpStatus.CONFLICT,
                userRoleResourceClient.grantUserClientRole(userRoleResourceTestHelper.buildUserRoleGrantClientRequest(clientUuid = clientUuid, userUuid = userUuid)),
                UserRoleErrorResponseModel.REQUESTED_ROLE_ALREADY_GRANTED
        )
    }

    @Test
    fun test() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val clientUuid = clientOrganizationResourceTestHelper.persistClientOrganization().response().uuid
        assertBasicSuccessResultResponse(userRoleResourceClient.grantUserClientRole(userRoleResourceTestHelper.buildUserRoleGrantClientRequest(userUuid = userUuid, clientUuid = clientUuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_VIEWER)))
    }
}
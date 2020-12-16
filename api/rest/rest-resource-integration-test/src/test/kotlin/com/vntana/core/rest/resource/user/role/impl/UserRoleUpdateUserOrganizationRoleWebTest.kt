package com.vntana.core.rest.resource.user.role.impl

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel
import com.vntana.core.rest.resource.user.role.AbstractUserRoleWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Vardan Aivazian
 * Date: 16.12.2020
 * Time: 12:40
 */
class UserRoleUpdateUserOrganizationRoleWebTest : AbstractUserRoleWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationRole(userRoleResourceTestHelper.buildUserUpdateOrganizationRoleRequest(userUuid = null)),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationRole(userRoleResourceTestHelper.buildUserUpdateOrganizationRoleRequest(userUuid = emptyString())),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationRole(userRoleResourceTestHelper.buildUserUpdateOrganizationRoleRequest(userUuid = blankString())),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationRole(userRoleResourceTestHelper.buildUserUpdateOrganizationRoleRequest(requestedUserUuid = null)),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationRole(userRoleResourceTestHelper.buildUserUpdateOrganizationRoleRequest(requestedUserUuid = emptyString())),
                UserRoleErrorResponseModel.MISSING_REQUESTED_USER_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationRole(userRoleResourceTestHelper.buildUserUpdateOrganizationRoleRequest(requestedUserUuid = blankString())),
                UserRoleErrorResponseModel.MISSING_REQUESTED_USER_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationRole(userRoleResourceTestHelper.buildUserUpdateOrganizationRoleRequest(organizationUuid = null)),
                UserRoleErrorResponseModel.MISSING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationRole(userRoleResourceTestHelper.buildUserUpdateOrganizationRoleRequest(organizationUuid = emptyString())),
                UserRoleErrorResponseModel.MISSING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationRole(userRoleResourceTestHelper.buildUserUpdateOrganizationRoleRequest(organizationUuid = blankString())),
                UserRoleErrorResponseModel.MISSING_ORGANIZATION_UUID
        )
    }

    @Test
    fun `test when organization not found`() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val result = userRoleResourceClient.updateUserOrganizationRole(userRoleResourceTestHelper.buildUserUpdateOrganizationRoleRequest(requestedUserUuid = userUuid))
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND, result, UserRoleErrorResponseModel.ORGANIZATION_NOT_FOUND)
    }

    @Test
    fun `test when user not found`() {
        val organizationUuid = userResourceTestHelper.persistUser().response().organizationUuid
        val result = userRoleResourceClient.updateUserOrganizationRole(userRoleResourceTestHelper.buildUserUpdateOrganizationRoleRequest(organizationUuid = organizationUuid))
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND, result, UserRoleErrorResponseModel.USER_NOT_FOUND)
    }

    @Test
    fun `test when requested user is organization owner`() {
        val user = userResourceTestHelper.persistUser().response()
        userRoleResourceClient.updateUserOrganizationRole(userRoleResourceTestHelper.buildUserUpdateOrganizationRoleRequest(
                organizationUuid = user.organizationUuid,
                requestedUserUuid = user.uuid
        )).let {
            assertBasicErrorResultResponse(HttpStatus.CONFLICT, it, UserRoleErrorResponseModel.REQUESTED_USER_IS_ORGANIZATION_OWNER)
        }
    }
    
    @Test
    fun `test when authorized user organization role not found`() {
        val ownerUser = userResourceTestHelper.persistUser().response()
        val user = userResourceTestHelper.persistUser().response()
        val requestedUser = userResourceTestHelper.persistUser().response()
        user.organizationUuid = ownerUser.organizationUuid
        requestedUser.organizationUuid = ownerUser.organizationUuid
        userRoleResourceClient.updateUserOrganizationRole(userRoleResourceTestHelper.buildUserUpdateOrganizationRoleRequest(
                userUuid = user.uuid,
                organizationUuid = requestedUser.organizationUuid,
                requestedUserUuid = requestedUser.uuid
        )).let {
            assertBasicErrorResultResponse(HttpStatus.FORBIDDEN, it, UserRoleErrorResponseModel.USER_ORGANIZATION_ROLE_NOT_FOUND)
        }
    }
    
    @Test
    fun `test when requested user has client roles`() {
        val user = userResourceTestHelper.persistUser().response()
        val requestedUser = userResourceTestHelper.persistUser().response()
        requestedUser.organizationUuid = user.organizationUuid
        val clientOrganization = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = requestedUser.organizationUuid).response()
        userRoleResourceTestHelper.grantUserClientRole(userUuid = requestedUser.uuid, clientUuid = clientOrganization.uuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_ADMIN)
        userRoleResourceClient.updateUserOrganizationRole(userRoleResourceTestHelper.buildUserUpdateOrganizationRoleRequest(
                userUuid = user.uuid,
                organizationUuid = user.organizationUuid,
                requestedUserUuid = requestedUser.uuid
        )).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body?.response()?.userUuid).isEqualTo(requestedUser.uuid)
            userResourceClient.getUsersByClientOrganization(clientOrganization.uuid)?.body?.response()?.run {
                assertThat(this.totalCount()).isEqualTo(0)
            }
            userResourceClient.getUserByOrganization(requestedUser.uuid, requestedUser.organizationUuid)?.body?.response()?.run {
                assertBasicSuccessResultResponse(it)
                assertThat(this.uuid).isEqualTo(requestedUser.uuid)
                assertThat(this.userRoleModel).isEqualTo(UserRoleModel.ORGANIZATION_ADMIN)
            }
        }
    }
}
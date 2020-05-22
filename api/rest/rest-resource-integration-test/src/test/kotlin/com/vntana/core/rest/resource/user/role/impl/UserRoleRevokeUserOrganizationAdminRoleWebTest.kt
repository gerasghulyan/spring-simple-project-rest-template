package com.vntana.core.rest.resource.user.role.impl

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel
import com.vntana.core.rest.resource.user.role.AbstractUserRoleWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 3:25 PM
 */
class UserRoleRevokeUserOrganizationAdminRoleWebTest : AbstractUserRoleWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.revokeUserOrganizationAdminRole(
                        resourceTestHelper.buildUserRoleRevokeOrganizationAdminRequest(organizationUuid = null)
                ),
                UserRoleErrorResponseModel.MISSING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.revokeUserOrganizationAdminRole(
                        resourceTestHelper.buildUserRoleRevokeOrganizationAdminRequest(organizationUuid = emptyString())
                ),
                UserRoleErrorResponseModel.MISSING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.revokeUserOrganizationAdminRole(
                        resourceTestHelper.buildUserRoleRevokeOrganizationAdminRequest(userUuid = null)
                ),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.revokeUserOrganizationAdminRole(
                        resourceTestHelper.buildUserRoleRevokeOrganizationAdminRequest(userUuid = emptyString())
                ),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
    }

    @Test
    fun `test when organization not found`() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val request = resourceTestHelper.buildUserRoleRevokeOrganizationAdminRequest(userUuid = userUuid)
        val result = userRoleResourceClient.revokeUserOrganizationAdminRole(request)
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND, result, UserRoleErrorResponseModel.ORGANIZATION_NOT_FOUND)
    }

    @Test
    fun `test when user not found`() {
        val userUuid = userResourceTestHelper.persistUser().response().organizationUuid
        val request = resourceTestHelper.buildUserRoleRevokeOrganizationAdminRequest(userUuid = userUuid)
        val result = userRoleResourceClient.revokeUserOrganizationAdminRole(request)
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND, result, UserRoleErrorResponseModel.ORGANIZATION_NOT_FOUND)
    }

    @Test
    fun `test when user has no admin role in organization`() {
        val userCreateResponseModel = userResourceTestHelper.persistUser().response()
        val organizationUuid = userCreateResponseModel.organizationUuid
        val userUuid = userCreateResponseModel.uuid
        val request = resourceTestHelper.buildUserRoleRevokeOrganizationAdminRequest(
                userUuid = userUuid,
                organizationUuid = organizationUuid
        )
        val result = userRoleResourceClient.revokeUserOrganizationAdminRole(request)
        assertBasicErrorResultResponse(HttpStatus.CONFLICT, result, UserRoleErrorResponseModel.REQUESTED_ROLE_IS_ABSENT)
    }

    @Test
    fun test() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val grantRequest = resourceTestHelper.buildUserRoleGrantOrganizationAdminRequest(userUuid = userUuid, organizationUuid = organizationUuid)
        val revokeRequest = resourceTestHelper.buildUserRoleRevokeOrganizationAdminRequest(userUuid = userUuid, organizationUuid = organizationUuid)
        val token1 = uuid()
        val token2 = uuid()
        authTokenResourceTestHelper.persistToken(userUuid = userUuid, token = token1)
        authTokenResourceTestHelper.persistToken(userUuid = userUuid, token = token2)
        userRoleResourceClient.grantUserOrganizationAdminRole(grantRequest)
        userRoleResourceClient.revokeUserOrganizationAdminRole(revokeRequest).let {
            assertBasicSuccessResultResponse(it)
            it?.body?.response()?.let { responseModel ->
                assertThat(responseModel.userUuid).isEqualTo(userUuid)
                userResourceClient.getUsersByOrganization(organizationUuid)?.body?.response()?.run {
                    assertThat(this.totalCount()).isEqualTo(1)
                    assertThat(this.items()[0].userRoleModel).isEqualTo(UserRoleModel.ORGANIZATION_OWNER)

                }
            }
        }
        assertThat(authTokenResourceClient.isExpired(token1)?.body?.response()?.expired).isTrue()
        assertThat(authTokenResourceClient.isExpired(token2)?.body?.response()?.expired).isTrue()
    }
}
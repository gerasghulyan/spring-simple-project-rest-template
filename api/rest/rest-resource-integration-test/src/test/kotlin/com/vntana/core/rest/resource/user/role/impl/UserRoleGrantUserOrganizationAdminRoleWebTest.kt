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
 * Time: 4:58 PM
 */
class UserRoleGrantUserOrganizationAdminRoleWebTest : AbstractUserRoleWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.grantUserOrganizationAdminRole(
                        resourceTestHelper.buildUserRoleGrantOrganizationAdminRequest(organizationUuid = null)
                ),
                UserRoleErrorResponseModel.MISSING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.grantUserOrganizationAdminRole(
                        resourceTestHelper.buildUserRoleGrantOrganizationAdminRequest(organizationUuid = emptyString())
                ),
                UserRoleErrorResponseModel.MISSING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.grantUserOrganizationAdminRole(
                        resourceTestHelper.buildUserRoleGrantOrganizationAdminRequest(userUuid = null)
                ),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.grantUserOrganizationAdminRole(
                        resourceTestHelper.buildUserRoleGrantOrganizationAdminRequest(userUuid = emptyString())
                ),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
    }

    @Test
    fun `test when organization not found`() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val request = resourceTestHelper.buildUserRoleGrantOrganizationAdminRequest(userUuid = userUuid)
        val result = userRoleResourceClient.grantUserOrganizationAdminRole(request)
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND, result, UserRoleErrorResponseModel.ORGANIZATION_NOT_FOUND)
    }

    @Test
    fun `test when user not found`() {
        val userUuid = userResourceTestHelper.persistUser().response().organizationUuid
        val request = resourceTestHelper.buildUserRoleGrantOrganizationAdminRequest(userUuid = userUuid)
        val result = userRoleResourceClient.grantUserOrganizationAdminRole(request)
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND, result, UserRoleErrorResponseModel.ORGANIZATION_NOT_FOUND)
    }

    @Test
    fun `test when user has no admin role in organization`() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val request = resourceTestHelper.buildUserRoleGrantOrganizationAdminRequest(userUuid = userUuid, organizationUuid = organizationUuid)
        userRoleResourceClient.grantUserOrganizationAdminRole(request)
        assertBasicErrorResultResponse(HttpStatus.CONFLICT, userRoleResourceClient.grantUserOrganizationAdminRole(request), UserRoleErrorResponseModel.REQUESTED_ROLE_ALREADY_GRANTED)
    }

    @Test
    fun test() {
        val adminUserUuid = userResourceTestHelper.persistUser().response().uuid
        val ownerUserUuid = userResourceTestHelper.persistUser().response().uuid
        val organizationUuid = organizationResourceTestHelper.persistOrganization(userUuid = ownerUserUuid).response().uuid
        val request = resourceTestHelper.buildUserRoleGrantOrganizationAdminRequest(userUuid = adminUserUuid, organizationUuid = organizationUuid)
        userRoleResourceClient.grantUserOrganizationAdminRole(request).let {
            assertBasicSuccessResultResponse(it)
            it?.body?.response()?.let { response ->
                assertThat(response.userUuid).isEqualTo(adminUserUuid)
                userResourceClient.getUsersByOrganization(organizationUuid)?.body?.response()?.run {
                    assertThat(this.totalCount()).isEqualTo(2)
                    this.items().forEach { responseModel ->
                        if (responseModel.userRoleModel.equals(UserRoleModel.ORGANIZATION_ADMIN)) {
                            assertThat(responseModel.uuid).isEqualTo(adminUserUuid)
                        } else {
                            assertThat(responseModel.uuid).isEqualTo(ownerUserUuid)
                        }
                    }
                }
            }
        }
    }

}
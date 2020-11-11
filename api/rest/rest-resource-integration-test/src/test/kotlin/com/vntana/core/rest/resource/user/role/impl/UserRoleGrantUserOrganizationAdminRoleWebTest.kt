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
                userRoleResourceTestHelper.grantUserOrganizationAdminRole(organizationUuid = null),
                UserRoleErrorResponseModel.MISSING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceTestHelper.grantUserOrganizationAdminRole(organizationUuid = emptyString()),
                UserRoleErrorResponseModel.MISSING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceTestHelper.grantUserOrganizationAdminRole(userUuid = null),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceTestHelper.grantUserOrganizationAdminRole(userUuid = emptyString()),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
    }

    @Test
    fun `test when organization not found`() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val result = userRoleResourceTestHelper.grantUserOrganizationAdminRole(userUuid = userUuid)
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND, result, UserRoleErrorResponseModel.ORGANIZATION_NOT_FOUND)
    }

    @Test
    fun `test when user not found`() {
        val userUuid = userResourceTestHelper.persistUser().response().organizationUuid
        val result = userRoleResourceTestHelper.grantUserOrganizationAdminRole(userUuid = userUuid)
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND, result, UserRoleErrorResponseModel.ORGANIZATION_NOT_FOUND)
    }

    @Test
    fun `test when user has no admin role in organization`() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        userRoleResourceTestHelper.grantUserOrganizationAdminRole(userUuid = userUuid, organizationUuid = organizationUuid)
        assertBasicErrorResultResponse(HttpStatus.CONFLICT, userRoleResourceTestHelper.grantUserOrganizationAdminRole(userUuid = userUuid, organizationUuid = organizationUuid), UserRoleErrorResponseModel.REQUESTED_ROLE_ALREADY_GRANTED)
    }

    @Test
    fun test() {
        val adminUserUuid = userResourceTestHelper.persistUser().response().uuid
        val ownerUserUuid = userResourceTestHelper.persistUser().response().uuid
        val organizationUuid = organizationResourceTestHelper.persistOrganization(userUuid = ownerUserUuid).response().uuid
        userRoleResourceTestHelper.grantUserOrganizationAdminRole(userUuid = adminUserUuid, organizationUuid = organizationUuid).let {
            assertBasicSuccessResultResponse(it)
            it.body?.response()?.let { response ->
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
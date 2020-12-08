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
                userRoleResourceClient.grantUserOrganizationAdminRole(userRoleResourceTestHelper.buildUserRoleGrantOrganizationAdminRequest(organizationUuid = null)),
                UserRoleErrorResponseModel.MISSING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.grantUserOrganizationAdminRole(userRoleResourceTestHelper.buildUserRoleGrantOrganizationAdminRequest(organizationUuid = emptyString())),
                UserRoleErrorResponseModel.MISSING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.grantUserOrganizationAdminRole(userRoleResourceTestHelper.buildUserRoleGrantOrganizationAdminRequest(userUuid = null)),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.grantUserOrganizationAdminRole(userRoleResourceTestHelper.buildUserRoleGrantOrganizationAdminRequest(userUuid = emptyString())),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
    }

    @Test
    fun `test when organization not found`() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val result = userRoleResourceClient.grantUserOrganizationAdminRole(
                userRoleResourceTestHelper.buildUserRoleGrantOrganizationAdminRequest(userUuid = userUuid))
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND, result, UserRoleErrorResponseModel.ORGANIZATION_NOT_FOUND)
    }

    @Test
    fun `test when user not found`() {
        val userUuid = userResourceTestHelper.persistUser().response().organizationUuid
        val result = userRoleResourceClient.grantUserOrganizationAdminRole(userRoleResourceTestHelper.buildUserRoleGrantOrganizationAdminRequest(userUuid = userUuid))
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND, result, UserRoleErrorResponseModel.ORGANIZATION_NOT_FOUND)
    }

    @Test
    fun `test when user has no admin role in organization`() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        userRoleResourceTestHelper.grantUserOrganizationAdminRole(userUuid = userUuid, organizationUuid = organizationUuid)
        assertBasicErrorResultResponse(HttpStatus.CONFLICT, userRoleResourceClient.grantUserOrganizationAdminRole(userRoleResourceTestHelper.buildUserRoleGrantOrganizationAdminRequest(userUuid = userUuid, organizationUuid = organizationUuid)), UserRoleErrorResponseModel.REQUESTED_ROLE_ALREADY_GRANTED)
    }

    @Test
    fun `test when user already has a role in current organization`() {
        val response = userResourceTestHelper.persistUser().response()
        val userUuid = response.uuid
        assertBasicErrorResultResponse(
                HttpStatus.CONFLICT,
                userRoleResourceClient.grantUserOrganizationAdminRole(userRoleResourceTestHelper.buildUserRoleGrantOrganizationAdminRequest(organizationUuid = response.organizationUuid, userUuid = userUuid)),
                UserRoleErrorResponseModel.REQUESTED_ROLE_ALREADY_GRANTED
        )
    }

    @Test
    fun `test when user has client roles`() {
        val adminUserUuid = userResourceTestHelper.persistUser().response().uuid
        val ownerUserUuid = userResourceTestHelper.persistUser().response().uuid
        val organizationUuid = organizationResourceTestHelper.persistOrganization(userUuid = ownerUserUuid).response().uuid
        val client1Uuid = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid).response().uuid
        val client2Uuid = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid).response().uuid
        userRoleResourceClient.grantUserClientRole(userRoleResourceTestHelper.buildUserRoleGrantClientRequest(userUuid = adminUserUuid, clientUuid = client1Uuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_ADMIN))
        userRoleResourceClient.grantUserClientRole(userRoleResourceTestHelper.buildUserRoleGrantClientRequest(userUuid = adminUserUuid, clientUuid = client2Uuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_VIEWER))
        userRoleResourceClient.grantUserOrganizationAdminRole(userRoleResourceTestHelper.buildUserRoleGrantOrganizationAdminRequest(userUuid = adminUserUuid, organizationUuid = organizationUuid)).let {
            assertBasicSuccessResultResponse(it)
            it.body?.response()?.let { response ->
                assertThat(response.userUuid).isEqualTo(adminUserUuid)
                userResourceClient.getUsersByOrganization(organizationUuid)?.body?.response()?.run {
                    assertThat(this.totalCount()).isEqualTo(2)
                    this.items().forEach { responseModel ->
                        if (responseModel.userRoleModel == UserRoleModel.ORGANIZATION_ADMIN) {
                            assertThat(responseModel.uuid).isEqualTo(adminUserUuid)
                        } else {
                            assertThat(responseModel.uuid).isEqualTo(ownerUserUuid)
                        }
                    }
                }
                userResourceClient.getUsersByClientOrganization(client1Uuid)?.body?.response()?.run {
                    assertThat(this.totalCount()).isEqualTo(0)
                }
                userResourceClient.getUsersByClientOrganization(client2Uuid)?.body?.response()?.run {
                    assertThat(this.totalCount()).isEqualTo(0)
                }
            }
        }
    }

    @Test
    fun test() {
        val adminUserUuid = userResourceTestHelper.persistUser().response().uuid
        val ownerUserUuid = userResourceTestHelper.persistUser().response().uuid
        val organizationUuid = organizationResourceTestHelper.persistOrganization(userUuid = ownerUserUuid).response().uuid
        userRoleResourceClient.grantUserOrganizationAdminRole(userRoleResourceTestHelper.buildUserRoleGrantOrganizationAdminRequest(userUuid = adminUserUuid, organizationUuid = organizationUuid)).let {
            assertBasicSuccessResultResponse(it)
            it.body?.response()?.let { response ->
                assertThat(response.userUuid).isEqualTo(adminUserUuid)
                userResourceClient.getUsersByOrganization(organizationUuid)?.body?.response()?.run {
                    assertThat(this.totalCount()).isEqualTo(2)
                    this.items().forEach { responseModel ->
                        if (responseModel.userRoleModel == UserRoleModel.ORGANIZATION_ADMIN) {
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
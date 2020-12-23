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
 * Time: 14:48
 */
class UserRoleUpdateUserOrganizationClientsRolesWebTest : AbstractUserRoleWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserUpdateOrganizationClientRoleRequest(userUuid = null)),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserUpdateOrganizationClientRoleRequest(userUuid = emptyString())),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserUpdateOrganizationClientRoleRequest(userUuid = blankString())),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserUpdateOrganizationClientRoleRequest(requestedUserUuid = null)),
                UserRoleErrorResponseModel.MISSING_REQUESTED_USER_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserUpdateOrganizationClientRoleRequest(requestedUserUuid = emptyString())),
                UserRoleErrorResponseModel.MISSING_REQUESTED_USER_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserUpdateOrganizationClientRoleRequest(requestedUserUuid = blankString())),
                UserRoleErrorResponseModel.MISSING_REQUESTED_USER_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserUpdateOrganizationClientRoleRequest(organizationUuid = null)),
                UserRoleErrorResponseModel.MISSING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserUpdateOrganizationClientRoleRequest(organizationUuid = emptyString())),
                UserRoleErrorResponseModel.MISSING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserUpdateOrganizationClientRoleRequest(organizationUuid = blankString())),
                UserRoleErrorResponseModel.MISSING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper
                        .buildUserUpdateOrganizationClientRoleRequest(
                                updateClientRoles = listOf(userRoleResourceTestHelper.buildUpdateClientRoleRequest(clientUuid = null)))),
                UserRoleErrorResponseModel.MISSING_CLIENT_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper
                        .buildUserUpdateOrganizationClientRoleRequest(
                                updateClientRoles = listOf(userRoleResourceTestHelper.buildUpdateClientRoleRequest(clientUuid = emptyString())))),
                UserRoleErrorResponseModel.MISSING_CLIENT_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper
                        .buildUserUpdateOrganizationClientRoleRequest(
                                updateClientRoles = listOf(userRoleResourceTestHelper.buildUpdateClientRoleRequest(clientUuid = blankString())))),
                UserRoleErrorResponseModel.MISSING_CLIENT_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper
                        .buildUserUpdateOrganizationClientRoleRequest(
                                updateClientRoles = listOf(userRoleResourceTestHelper.buildUpdateClientRoleRequest(userRoleModel = null)))),
                UserRoleErrorResponseModel.MISSING_CLIENT_ROLE
        )
        assertBasicErrorResultResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper
                        .buildUserUpdateOrganizationClientRoleRequest(
                                updateClientRoles = listOf(userRoleResourceTestHelper.buildUpdateClientRoleRequest(userRoleModel = UserRoleModel.ORGANIZATION_ADMIN)))),
                UserRoleErrorResponseModel.REQUEST_ROLE_IS_NOT_CLIENT_RELATED
        )
    }

    @Test
    fun `test when organization not found`() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val result = userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserUpdateOrganizationClientRoleRequest(requestedUserUuid = userUuid))
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND, result, UserRoleErrorResponseModel.ORGANIZATION_NOT_FOUND)
    }

    @Test
    fun `test when user not found`() {
        val organizationUuid = userResourceTestHelper.persistUser().response().organizationUuid
        val result = userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserUpdateOrganizationClientRoleRequest(organizationUuid = organizationUuid))
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND, result, UserRoleErrorResponseModel.USER_NOT_FOUND)
    }

    @Test
    fun `test when requested user is organization owner`() {
        val user = userResourceTestHelper.persistUser().response()
        userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserUpdateOrganizationClientRoleRequest(
                organizationUuid = user.organizationUuid,
                requestedUserUuid = user.uuid
        )).let {
            assertBasicErrorResultResponse(HttpStatus.CONFLICT, it, UserRoleErrorResponseModel.REQUESTED_USER_IS_ORGANIZATION_OWNER)
        }
    }

    @Test
    fun `test when authorized user role is low priority`() {
        val ownerUser = userResourceTestHelper.persistUser().response()
        val user = userResourceTestHelper.persistUser().response()
        val requestedUser = userResourceTestHelper.persistUser().response()
        user.organizationUuid = ownerUser.organizationUuid
        requestedUser.organizationUuid = ownerUser.organizationUuid
        val clientOrganization = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = user.organizationUuid).response()
        userRoleResourceTestHelper.grantUserClientRole(userUuid = user.uuid, clientUuid = clientOrganization.uuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)
        val updateClientRoleRequest = userRoleResourceTestHelper.buildUpdateClientRoleRequest(clientUuid = clientOrganization.uuid, userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_ADMIN)
        userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserUpdateOrganizationClientRoleRequest(
                userUuid = user.uuid,
                organizationUuid = requestedUser.organizationUuid,
                requestedUserUuid = requestedUser.uuid,
                updateClientRoles = listOf(updateClientRoleRequest)
        )).let {
            assertBasicErrorResultResponse(HttpStatus.FORBIDDEN, it, UserRoleErrorResponseModel.INCORRECT_PERMISSION_GRANT_CLIENT_ROLE)
        }
    }

    @Test
    fun `test when authorized user is owner and requested user is org admin`() {
        val user = userResourceTestHelper.persistUser().response()
        val organizationUuid = organizationResourceTestHelper.persistOrganization(userUuid = user.uuid).response().uuid
        val requestedUser = userResourceTestHelper.persistUser().response()
        userRoleResourceTestHelper.grantUserOrganizationAdminRole(userUuid = requestedUser.uuid, organizationUuid = organizationUuid)
        val clientOrganization = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid).response()
        val updateClientRoleRequest = userRoleResourceTestHelper.buildUpdateClientRoleRequest(clientUuid = clientOrganization.uuid, userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_ADMIN)
        userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserUpdateOrganizationClientRoleRequest(
                userUuid = user.uuid,
                organizationUuid = organizationUuid,
                requestedUserUuid = requestedUser.uuid,
                updateClientRoles = listOf(updateClientRoleRequest)
        )).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body?.response()?.userUuid).isEqualTo(requestedUser.uuid)
            userResourceClient.getUsersByClientOrganization(clientOrganization.uuid)?.body?.response()?.run {
                assertThat(this.totalCount()).isEqualTo(1)
                assertThat(this.items()[0].userRoleModel).isEqualTo(updateClientRoleRequest.clientRole)
                assertThat(this.items()[0].uuid).isEqualTo(requestedUser.uuid)
            }
            userResourceClient.getUsersByOrganization(organizationUuid)?.body?.response()?.run {
                assertBasicSuccessResultResponse(it)
                assertThat(this.totalCount()).isEqualTo(1)
                assertThat(this.items()[0].userRoleModel).isEqualTo(UserRoleModel.ORGANIZATION_OWNER)
            }
        }
    }

    @Test
    fun `test when authorized user is owner and requested user has client roles for update`() {
        val user = userResourceTestHelper.persistUser().response()
        val organizationUuid = organizationResourceTestHelper.persistOrganization(userUuid = user.uuid).response().uuid
        val requestedUser = userResourceTestHelper.persistUser().response()
        val clientOrganization1 = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid).response()
        val clientOrganization2 = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid).response()
        val clientOrganization3 = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid).response()
        userRoleResourceTestHelper.grantUserClientRole(userUuid = requestedUser.uuid, clientUuid = clientOrganization1.uuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)
        userRoleResourceTestHelper.grantUserClientRole(userUuid = requestedUser.uuid, clientUuid = clientOrganization2.uuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_ADMIN)
        val updateClientRoleRequest = userRoleResourceTestHelper.buildUpdateClientRoleRequest(clientUuid = clientOrganization2.uuid, userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_VIEWER)
        val grantClientRoleRequest = userRoleResourceTestHelper.buildUpdateClientRoleRequest(clientUuid = clientOrganization3.uuid, userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_ADMIN)
        userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserUpdateOrganizationClientRoleRequest(
                userUuid = user.uuid,
                organizationUuid = organizationUuid,
                requestedUserUuid = requestedUser.uuid,
                updateClientRoles = listOf(updateClientRoleRequest, grantClientRoleRequest)
        )).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body?.response()?.userUuid).isEqualTo(requestedUser.uuid)
            userResourceClient.getUsersByClientOrganization(clientOrganization1.uuid)?.body?.response()?.run {
                assertThat(this.totalCount()).isEqualTo(0)
            }
            userResourceClient.getUsersByClientOrganization(clientOrganization2.uuid)?.body?.response()?.run {
                assertThat(this.totalCount()).isEqualTo(1)
                assertThat(this.items()[0].userRoleModel).isEqualTo(updateClientRoleRequest.clientRole)
                assertThat(this.items()[0].uuid).isEqualTo(requestedUser.uuid)
            }
            userResourceClient.getUsersByClientOrganization(clientOrganization3.uuid)?.body?.response()?.run {
                assertThat(this.totalCount()).isEqualTo(1)
                assertThat(this.items()[0].userRoleModel).isEqualTo(grantClientRoleRequest.clientRole)
                assertThat(this.items()[0].uuid).isEqualTo(requestedUser.uuid)
            }
            userResourceClient.getUsersByOrganization(organizationUuid)?.body?.response()?.run {
                assertBasicSuccessResultResponse(it)
                assertThat(this.totalCount()).isEqualTo(1)
                assertThat(this.items()[0].userRoleModel).isEqualTo(UserRoleModel.ORGANIZATION_OWNER)
            }
        }
    }

    @Test
    fun `test when authorized user is organization admin`() {
        val user = userResourceTestHelper.persistUser().response()
        val requestedUser = userResourceTestHelper.persistUser().response()
        requestedUser.organizationUuid = user.organizationUuid
        userRoleResourceTestHelper.grantUserOrganizationAdminRole(userUuid = user.uuid, organizationUuid = user.organizationUuid)
        val clientOrganization = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = user.organizationUuid).response()
        val updateClientRoleRequest = userRoleResourceTestHelper.buildUpdateClientRoleRequest(clientUuid = clientOrganization.uuid, userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_ADMIN)
        userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserUpdateOrganizationClientRoleRequest(
                userUuid = user.uuid,
                organizationUuid = requestedUser.organizationUuid,
                requestedUserUuid = requestedUser.uuid,
                updateClientRoles = listOf(updateClientRoleRequest)
        )).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body?.response()?.userUuid).isEqualTo(requestedUser.uuid)
            userResourceClient.getUsersByClientOrganization(clientOrganization.uuid)?.body?.response()?.run {
                assertThat(this.totalCount()).isEqualTo(1)
                assertThat(this.items()[0].userRoleModel).isEqualTo(updateClientRoleRequest.clientRole)
                assertThat(this.items()[0].uuid).isEqualTo(requestedUser.uuid)
            }
            userResourceClient.getUsersByOrganization(requestedUser.organizationUuid)?.body?.response()?.run {
                assertBasicSuccessResultResponse(it)
                assertThat(this.totalCount()).isEqualTo(1)
                assertThat(this.items()[0].userRoleModel).isEqualTo(UserRoleModel.ORGANIZATION_OWNER)
            }
        }
    }

    @Test
    fun `test when authorized user and requested user has client roles`() {
        val user = userResourceTestHelper.persistUser().response()
        val requestedUser = userResourceTestHelper.persistUser().response()
        requestedUser.organizationUuid = user.organizationUuid
        val clientOrganization1 = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = user.organizationUuid).response()
        val clientOrganization2 = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = user.organizationUuid).response()
        userRoleResourceTestHelper.grantUserClientRole(userUuid = user.uuid, clientUuid = clientOrganization1.uuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)
        userRoleResourceTestHelper.grantUserClientRole(userUuid = user.uuid, clientUuid = clientOrganization2.uuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_ADMIN)
        val updateClientRoleRequest1 = userRoleResourceTestHelper.buildUpdateClientRoleRequest(clientUuid = clientOrganization1.uuid, userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_VIEWER)
        val updateClientRoleRequest2 = userRoleResourceTestHelper.buildUpdateClientRoleRequest(clientUuid = clientOrganization2.uuid, userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_ADMIN)
        userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserUpdateOrganizationClientRoleRequest(
                userUuid = user.uuid,
                organizationUuid = requestedUser.organizationUuid,
                requestedUserUuid = requestedUser.uuid,
                updateClientRoles = listOf(updateClientRoleRequest1, updateClientRoleRequest2)
        )).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body?.response()?.userUuid).isEqualTo(requestedUser.uuid)
            userResourceClient.getUsersByClientOrganization(clientOrganization1.uuid)?.body?.response()?.run {
                assertThat(this.totalCount()).isEqualTo(1)
                assertThat(this.items()[0].userRoleModel).isEqualTo(updateClientRoleRequest1.clientRole)
                assertThat(this.items()[0].uuid).isEqualTo(requestedUser.uuid)
            }
            userResourceClient.getUsersByClientOrganization(clientOrganization2.uuid)?.body?.response()?.run {
                assertThat(this.totalCount()).isEqualTo(1)
                assertThat(this.items()[0].userRoleModel).isEqualTo(updateClientRoleRequest2.clientRole)
                assertThat(this.items()[0].uuid).isEqualTo(requestedUser.uuid)
            }
        }
    }

    @Test
    fun `test when authorized user and requested user has client roles bu not updated all roles`() {
        val user = userResourceTestHelper.persistUser().response()
        val requestedUser = userResourceTestHelper.persistUser().response()
        requestedUser.organizationUuid = user.organizationUuid
        val clientOrganization1 = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = user.organizationUuid).response()
        val clientOrganization2 = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = user.organizationUuid).response()
        userRoleResourceTestHelper.grantUserClientRole(userUuid = user.uuid, clientUuid = clientOrganization1.uuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)
        userRoleResourceTestHelper.grantUserClientRole(userUuid = requestedUser.uuid, clientUuid = clientOrganization2.uuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_ADMIN)
        val updateClientRoleRequest1 = userRoleResourceTestHelper.buildUpdateClientRoleRequest(clientUuid = clientOrganization1.uuid, userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_VIEWER)
        val updateClientRoleRequest2 = userRoleResourceTestHelper.buildUpdateClientRoleRequest(clientUuid = clientOrganization2.uuid, userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_ADMIN)
        userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserUpdateOrganizationClientRoleRequest(
                userUuid = user.uuid,
                organizationUuid = requestedUser.organizationUuid,
                requestedUserUuid = requestedUser.uuid,
                updateClientRoles = listOf(updateClientRoleRequest1, updateClientRoleRequest2)
        )).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body?.response()?.userUuid).isEqualTo(requestedUser.uuid)
            userResourceClient.getUsersByClientOrganization(clientOrganization1.uuid)?.body?.response()?.run {
                assertThat(this.totalCount()).isEqualTo(1)
                assertThat(this.items()[0].userRoleModel).isEqualTo(updateClientRoleRequest1.clientRole)
                assertThat(this.items()[0].uuid).isEqualTo(requestedUser.uuid)
            }
            userResourceClient.getUsersByClientOrganization(clientOrganization2.uuid)?.body?.response()?.run {
                assertThat(this.totalCount()).isEqualTo(1)
                assertThat(this.items()[0].userRoleModel).isEqualTo(updateClientRoleRequest2.clientRole)
                assertThat(this.items()[0].uuid).isEqualTo(requestedUser.uuid)
            }
        }
    }

    @Test
    fun `test when revoked client roles`() {
        val user = userResourceTestHelper.persistUser().response()
        val requestedUser = userResourceTestHelper.persistUser().response()
        requestedUser.organizationUuid = user.organizationUuid
        val clientOrganization1 = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = user.organizationUuid).response()
        val clientOrganization2 = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = user.organizationUuid).response()
        val clientOrganization3 = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = user.organizationUuid).response()
        userRoleResourceTestHelper.grantUserClientRole(userUuid = user.uuid, clientUuid = clientOrganization1.uuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)
        userRoleResourceTestHelper.grantUserClientRole(userUuid = user.uuid, clientUuid = clientOrganization2.uuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_ADMIN)
        userRoleResourceTestHelper.grantUserClientRole(userUuid = requestedUser.uuid, clientUuid = clientOrganization3.uuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)
        val updateClientRoleRequest1 = userRoleResourceTestHelper.buildUpdateClientRoleRequest(clientUuid = clientOrganization1.uuid, userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_VIEWER)
        val updateClientRoleRequest2 = userRoleResourceTestHelper.buildUpdateClientRoleRequest(clientUuid = clientOrganization2.uuid, userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_ADMIN)
        userRoleResourceClient.updateUserOrganizationClientsRoles(userRoleResourceTestHelper.buildUserUpdateOrganizationClientRoleRequest(
                userUuid = user.uuid,
                organizationUuid = requestedUser.organizationUuid,
                requestedUserUuid = requestedUser.uuid,
                updateClientRoles = listOf(updateClientRoleRequest1, updateClientRoleRequest2)
        )).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body?.response()?.userUuid).isEqualTo(requestedUser.uuid)
            userResourceClient.getUsersByClientOrganization(clientOrganization1.uuid)?.body?.response()?.run {
                assertThat(this.totalCount()).isEqualTo(1)
                assertThat(this.items()[0].userRoleModel).isEqualTo(updateClientRoleRequest1.clientRole)
                assertThat(this.items()[0].uuid).isEqualTo(requestedUser.uuid)
            }
            userResourceClient.getUsersByClientOrganization(clientOrganization2.uuid)?.body?.response()?.run {
                assertThat(this.totalCount()).isEqualTo(1)
                assertThat(this.items()[0].userRoleModel).isEqualTo(updateClientRoleRequest2.clientRole)
                assertThat(this.items()[0].uuid).isEqualTo(requestedUser.uuid)
            }
            userResourceClient.getUsersByClientOrganization(clientOrganization3.uuid)?.body?.response()?.run {
                assertThat(this.totalCount()).isEqualTo(0)
            }
        }
    }
}
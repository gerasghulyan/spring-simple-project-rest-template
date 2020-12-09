package com.vntana.core.rest.resource.client.test

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.client.request.GetClientsByUserAndBulkOrganizationRequest
import com.vntana.core.rest.resource.client.AbstractClientOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 11/11/2019
 * Time: 9:22 PM
 */
class ClientOrganizationGetClientBulkOrganizationWebTest : AbstractClientOrganizationWebTest() {

    @Test
    fun `test getByUserAndBulkOrganizations for user`() {
        val userCreationResponse = userResourceTestHelper.persistUser().response()
        val userCreationResponse2 = userResourceTestHelper.persistUser().response()
        val clientUuid = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = userCreationResponse.organizationUuid).response().uuid
        val clientUuid2 = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = userCreationResponse.organizationUuid).response().uuid
        userRoleResourceTestHelper.grantUserClientRole(userUuid = userCreationResponse.uuid, clientUuid = clientUuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)
        userRoleResourceTestHelper.grantUserClientRole(userUuid = userCreationResponse2.uuid, clientUuid = clientUuid2, userRole = UserRoleModel.CLIENT_ORGANIZATION_VIEWER)
        clientOrganizationResourceClient.getByUserAndBulkOrganizations(userCreationResponse2.uuid, GetClientsByUserAndBulkOrganizationRequest(listOf(userCreationResponse.organizationUuid))).let {
            assertBasicSuccessResultResponse(it)
            val items = it.response().items()
            assertThat(items.size).isEqualTo(1)
            items.forEach { item ->
                run {
                    assertThat(item.uuid).isEqualTo(clientUuid2)
                    assertThat(item.role).isEqualTo(UserRoleModel.CLIENT_ORGANIZATION_VIEWER)
                }
            }
        }
    }

    @Test
    fun `test getByUserAndBulkOrganizations for super admin user`() {
        val userCreationResponse = userResourceTestHelper.persistUser().response()
        userRoleResourceTestHelper.grantSuperAdmin(userUuid = userCreationResponse.uuid)
        val clientUuid = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = userCreationResponse.organizationUuid).response().uuid
        val clientUuid2 = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = userCreationResponse.organizationUuid).response().uuid
        clientOrganizationResourceClient.getByUserAndBulkOrganizations(userCreationResponse.uuid, GetClientsByUserAndBulkOrganizationRequest(listOf(userCreationResponse.organizationUuid))).let {
            assertBasicSuccessResultResponse(it)
            val items = it.response().items()
            assertThat(items.size).isEqualTo(2)
            assertThat(items.map { item -> item.uuid }.toList()).containsOnly(clientUuid, clientUuid2)
        }
    }

    @Test
    fun `test getByUserAndBulkOrganizations for organization owner user`() {
        val userCreationResponse = userResourceTestHelper.persistUser().response()
        val clientUuid = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = userCreationResponse.organizationUuid).response().uuid
        val clientUuid2 = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = userCreationResponse.organizationUuid).response().uuid
        clientOrganizationResourceClient.getByUserAndBulkOrganizations(userCreationResponse.uuid, GetClientsByUserAndBulkOrganizationRequest(listOf(userCreationResponse.organizationUuid))).let {
            assertBasicSuccessResultResponse(it)
            val items = it.response().items()
            assertThat(items.size).isEqualTo(2)
            assertThat(items.map { item -> item.uuid }.toList()).containsOnly(clientUuid, clientUuid2)
        }
    }

    @Test
    fun `test getByUserAndBulkOrganizations for organization admin user`() {
        val userCreationResponse = userResourceTestHelper.persistUser().response()
        val userCreationResponse2 = userResourceTestHelper.persistUser().response()
        userRoleResourceTestHelper.grantUserOrganizationAdminRole(userUuid = userCreationResponse2.uuid, organizationUuid = userCreationResponse.organizationUuid)
        val clientUuid = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = userCreationResponse.organizationUuid).response().uuid
        val clientUuid2 = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = userCreationResponse.organizationUuid).response().uuid
        clientOrganizationResourceClient.getByUserAndBulkOrganizations(userCreationResponse2.uuid, GetClientsByUserAndBulkOrganizationRequest(listOf(userCreationResponse.organizationUuid))).let {
            assertBasicSuccessResultResponse(it)
            val items = it.response().items()
            assertThat(items.size).isEqualTo(2)
            assertThat(items.map { item -> item.uuid }.toList()).containsOnly(clientUuid, clientUuid2)
        }
    }
}
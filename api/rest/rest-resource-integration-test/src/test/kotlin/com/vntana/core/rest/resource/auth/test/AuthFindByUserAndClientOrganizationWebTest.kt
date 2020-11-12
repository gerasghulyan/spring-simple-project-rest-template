package com.vntana.core.rest.resource.auth.test

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.resource.auth.AbstractAuthWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/13/20
 * Time: 11:32 AM
 */
class AuthFindByUserAndClientOrganizationWebTest : AbstractAuthWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                authResourceClient.findByUserAndClientOrganization(userResourceTestHelper.buildFindUserByUuidAndClientOrganizationRequest(uuid = null)),
                UserErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                authResourceClient.findByUserAndClientOrganization(userResourceTestHelper.buildFindUserByUuidAndClientOrganizationRequest(uuid = emptyString())),
                UserErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                authResourceClient.findByUserAndClientOrganization(userResourceTestHelper.buildFindUserByUuidAndClientOrganizationRequest(clientUuid = null)),
                UserErrorResponseModel.MISSING_CLIENT
        )
        assertBasicErrorResultResponse(
                authResourceClient.findByUserAndClientOrganization(userResourceTestHelper.buildFindUserByUuidAndClientOrganizationRequest(clientUuid = emptyString())),
                UserErrorResponseModel.MISSING_CLIENT
        )
    }

    @Test
    fun `test when user not found`() {
        assertBasicErrorResultResponse(
                authResourceClient.findByUserAndClientOrganization(userResourceTestHelper.buildFindUserByUuidAndClientOrganizationRequest()),
                UserErrorResponseModel.USER_NOT_FOUND
        )
    }

    @Test
    fun `test when user role not found`() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        assertBasicErrorResultResponse(
                authResourceClient.findByUserAndClientOrganization(userResourceTestHelper.buildFindUserByUuidAndClientOrganizationRequest(uuid = userUuid)),
                UserErrorResponseModel.NOT_FOUND_FOR_ROLE
        )
    }

    @Test
    fun `test when is also super admin`() {
        val userCreateResponse = userResourceTestHelper.persistUser(userResourceTestHelper.buildCreateUserRequest()).response()
        val userUuid = userCreateResponse.uuid
        val organizationUuid = userCreateResponse.organizationUuid
        val clientUuid = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid).response().uuid
        val request = userResourceTestHelper.buildFindUserByUuidAndClientOrganizationRequest(
                uuid = userUuid,
                clientUuid = clientUuid
        )
        userRoleResourceTestHelper.grantSuperAdmin(userUuid)
        authResourceClient.findByUserAndClientOrganization(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().userRole).isEqualTo(UserRoleModel.SUPER_ADMIN)
            assertThat(it.response().clientUuid).isEqualTo(clientUuid)
            assertThat(it.response().organizationUuid).isEqualTo(organizationUuid)
            assertThat(it.response().uuid).isEqualTo(userUuid)
            assertThat(it.response().superAdmin).isTrue()
        }
    }

    @Test
    fun `test with client organization role`() {
        val userCreateResponse = userResourceTestHelper.persistUser(userResourceTestHelper.buildCreateUserRequest()).response()
        val userUuid = userCreateResponse.uuid
        val organizationUuid = userCreateResponse.organizationUuid
        val clientUuid = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid).response().uuid
        userRoleResourceTestHelper.grantUserClientRole(userUuid, clientUuid)
        val request = userResourceTestHelper.buildFindUserByUuidAndClientOrganizationRequest(
                uuid = userUuid,
                clientUuid = clientUuid
        )
        authResourceClient.findByUserAndClientOrganization(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().userRole).isEqualTo(UserRoleModel.CLIENT_ORGANIZATION_ADMIN)
            assertThat(it.response().clientUuid).isEqualTo(clientUuid)
            assertThat(it.response().organizationUuid).isEqualTo(organizationUuid)
            assertThat(it.response().uuid).isEqualTo(userUuid)
            assertThat(it.response().superAdmin).isFalse()
        }
    }
}
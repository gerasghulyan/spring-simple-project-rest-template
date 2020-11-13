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
class AuthFindByUserAndOrganizationWebTest : AbstractAuthWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                authResourceClient.findByUserAndOrganization(userResourceTestHelper.buildFindUserByUuidAndOrganizationRequest(uuid = null)),
                UserErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                authResourceClient.findByUserAndOrganization(userResourceTestHelper.buildFindUserByUuidAndOrganizationRequest(uuid = emptyString())),
                UserErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                authResourceClient.findByUserAndOrganization(userResourceTestHelper.buildFindUserByUuidAndOrganizationRequest(organizationUuid = null)),
                UserErrorResponseModel.MISSING_ORGANIZATION
        )
        assertBasicErrorResultResponse(
                authResourceClient.findByUserAndOrganization(userResourceTestHelper.buildFindUserByUuidAndOrganizationRequest(organizationUuid = emptyString())),
                UserErrorResponseModel.MISSING_ORGANIZATION
        )
    }

    @Test
    fun `test when user not found`() {
        assertBasicErrorResultResponse(
                authResourceClient.findByUserAndOrganization(userResourceTestHelper.buildFindUserByUuidAndOrganizationRequest()),
                UserErrorResponseModel.USER_NOT_FOUND
        )
    }

    @Test
    fun `test when user role not found`() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        assertBasicErrorResultResponse(
                authResourceClient.findByUserAndOrganization(userResourceTestHelper.buildFindUserByUuidAndOrganizationRequest(uuid = userUuid)),
                UserErrorResponseModel.NOT_FOUND_FOR_ROLE
        )
    }

    @Test
    fun `test when is also super admin`() {
        val response = userResourceTestHelper.persistUser(userResourceTestHelper.buildCreateUserRequest()).response()
        val organizationUuid = response.organizationUuid
        val userUuid = response.uuid
        val request = userResourceTestHelper.buildFindUserByUuidAndOrganizationRequest(
                uuid = userUuid,
                organizationUuid = organizationUuid
        )
        userRoleResourceTestHelper.grantSuperAdmin(userUuid)
        authResourceClient.findByUserAndOrganization(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().userRole).isEqualTo(UserRoleModel.SUPER_ADMIN)
            assertThat(it.response().organizationUuid).isEqualTo(organizationUuid)
            assertThat(it.response().uuid).isEqualTo(userUuid)
            assertThat(it.response().superAdmin).isTrue()
        }
    }

    @Test
    fun `test with client organization role`() {
        val user = userResourceTestHelper.persistUser()
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val clientOrganization = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid)
        val userUuid = user.response().uuid
        userRoleResourceTestHelper.grantUserClientRole(userUuid, clientOrganization.response().uuid)
        val request = userResourceTestHelper.buildFindUserByUuidAndOrganizationRequest(
                uuid = userUuid,
                organizationUuid = organizationUuid
        )
        authResourceClient.findByUserAndOrganization(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().userRole).isEqualTo(UserRoleModel.ORGANIZATION_CLIENTS_VIEWER)
            assertThat(it.response().organizationUuid).isEqualTo(organizationUuid)
            assertThat(it.response().uuid).isEqualTo(userUuid)
            assertThat(it.response().superAdmin).isFalse()
        }
    }

    @Test
    fun test() {
        val response = userResourceTestHelper.persistUser(userResourceTestHelper.buildCreateUserRequest()).response()
        val organizationUuid = response.organizationUuid
        val userUuid = response.uuid
        val request = userResourceTestHelper.buildFindUserByUuidAndOrganizationRequest(
                uuid = userUuid,
                organizationUuid = organizationUuid
        )
        authResourceClient.findByUserAndOrganization(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().userRole).isEqualTo(UserRoleModel.ORGANIZATION_OWNER)
            assertThat(it.response().organizationUuid).isEqualTo(organizationUuid)
            assertThat(it.response().uuid).isEqualTo(userUuid)
            assertThat(it.response().superAdmin).isFalse()
        }
    }
}
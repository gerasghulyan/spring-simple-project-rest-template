package com.vntana.core.rest.resource.auth.test

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.model.user.role.request.UserRoleGrantSuperAdminRequest
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
                authResourceClient.findByEmailAndOrganization(userResourceTestHelper.buildFindUserByUuidAndOrganizationRequest(uuid = null)),
                UserErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                authResourceClient.findByEmailAndOrganization(userResourceTestHelper.buildFindUserByUuidAndOrganizationRequest(uuid = emptyString())),
                UserErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                authResourceClient.findByEmailAndOrganization(userResourceTestHelper.buildFindUserByUuidAndOrganizationRequest(organizationUuid = null)),
                UserErrorResponseModel.MISSING_ORGANIZATION
        )
        assertBasicErrorResultResponse(
                authResourceClient.findByEmailAndOrganization(userResourceTestHelper.buildFindUserByUuidAndOrganizationRequest(organizationUuid = emptyString())),
                UserErrorResponseModel.MISSING_ORGANIZATION
        )
    }

    @Test
    fun `test when not found`() {
        assertBasicErrorResultResponse(
                authResourceClient.findByEmailAndOrganization(userResourceTestHelper.buildFindUserByUuidAndOrganizationRequest()),
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
        userRoleResourceClient.grantSuperAdmin(UserRoleGrantSuperAdminRequest(userUuid))
        authResourceClient.findByEmailAndOrganization(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().userRole).isEqualTo(UserRoleModel.ORGANIZATION_OWNER)
            assertThat(it.response().organizationUuid).isEqualTo(organizationUuid)
            assertThat(it.response().uuid).isEqualTo(userUuid)
            assertThat(it.response().superAdmin).isTrue()
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
        authResourceClient.findByEmailAndOrganization(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().userRole).isEqualTo(UserRoleModel.ORGANIZATION_OWNER)
            assertThat(it.response().organizationUuid).isEqualTo(organizationUuid)
            assertThat(it.response().uuid).isEqualTo(userUuid)
            assertThat(it.response().superAdmin).isFalse()
        }
    }
}
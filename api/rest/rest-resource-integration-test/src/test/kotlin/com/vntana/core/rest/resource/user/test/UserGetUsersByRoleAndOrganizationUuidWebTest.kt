package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Manuk Gharslyan.
 * Date: 4/7/2020
 * Time: 3:11 PM
 */
class UserGetUsersByRoleAndOrganizationUuidWebTest : AbstractUserWebTest() {

    @Test
    fun `test find`() {
        val slug = uuid()
        resourceHelper.persistUser(createUserRequest = resourceHelper.buildCreateUserRequest(organizationSlug = slug))
        val organizationUuid = organizationResourceTestHelper.getOrganizationBySlug(slug = slug).response().uuid
        assertBasicSuccessResultResponse(userResourceClient.getUsersByRoleAndOrganizationUuid(UserRoleModel.ORGANIZATION_OWNER, organizationUuid))
    }

    @Test
    fun `test not found`() {
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val response = userResourceClient.getUsersByRoleAndOrganizationUuid(UserRoleModel.CLIENT_ORGANIZATION_VIEWER, organizationUuid)
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND, response,
                UserErrorResponseModel.NOT_FOUND_FOR_ROLE, UserErrorResponseModel.NOT_FOUND_FOR_ORGANIZATION)
    }
}
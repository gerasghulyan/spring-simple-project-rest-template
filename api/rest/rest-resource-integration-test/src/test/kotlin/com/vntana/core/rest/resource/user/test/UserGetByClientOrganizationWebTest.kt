package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.error.UserErrorResponseModel.NOT_FOUND_FOR_CLIENT_ORGANIZATION
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus.NOT_FOUND

/**
 * Created by Manuk Gharslyan.
 * Date: 5/7/20
 * Time: 5:38 PM
 */
class UserGetByClientOrganizationWebTest : AbstractUserWebTest() {

    @Test
    fun `test when organization not found`() {
        assertBasicErrorResultResponse(NOT_FOUND, userResourceClient.getUsersByClientOrganization(uuid()), NOT_FOUND_FOR_CLIENT_ORGANIZATION)
    }

    @Test
    fun `test multiple users`() {
        val userUuid1 = resourceHelper.persistUser().response().uuid
        val userUuid2 = resourceHelper.persistUser().response().uuid
        val userUuid3 = resourceHelper.persistUser().response().uuid
        val clientUuid = clientOrganizationResourceTestHelper.persistClientOrganization().response().uuid
        userRoleResourceTestHelper.grantUserClientRole(userUuid = userUuid1, clientUuid = clientUuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_VIEWER)
        userRoleResourceTestHelper.grantUserClientRole(userUuid = userUuid2, clientUuid = clientUuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)
        userRoleResourceTestHelper.grantUserClientRole(userUuid = userUuid3, clientUuid = clientUuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_ADMIN)
        userResourceClient.getUsersByClientOrganization(clientUuid).let { responseEntity ->
            assertBasicSuccessResultResponse(responseEntity)
            responseEntity?.body?.response()?.let {
                assertThat(it.totalCount()).isEqualTo(3)
            }
        }
    }
}
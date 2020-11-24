package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.error.UserErrorResponseModel.NOT_FOUND_FOR_ORGANIZATION
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus.NOT_FOUND

/**
 * Created by Manuk Gharslyan.
 * Date: 5/7/20
 * Time: 5:38 PM
 */
class UserGetClientsByOrganizationWebTest : AbstractUserWebTest() {

    @Test
    fun `test when organization not found`() {
        assertBasicErrorResultResponse(NOT_FOUND, userResourceClient.getUsersOfClientsByOrganization(uuid()), NOT_FOUND_FOR_ORGANIZATION)
    }

    @Test
    fun `test multiple users`() {
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val createUserResponse2 = resourceHelper.persistUser().response()
        val createUserResponse3 = resourceHelper.persistUser().response()
        val userUuid2 = createUserResponse2.uuid
        val userUuid3 = createUserResponse3.uuid
        val clientUuid = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid).response().uuid
        userRoleResourceTestHelper.grantUserClientRole(userUuid = userUuid3, clientUuid = clientUuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_VIEWER)
        userRoleResourceTestHelper.grantUserClientRole(userUuid = userUuid2, clientUuid = clientUuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)
        userResourceClient.getUsersOfClientsByOrganization(organizationUuid).let { responseEntity ->
            assertBasicSuccessResultResponse(responseEntity)
            responseEntity?.body?.response()?.let {
                assertThat(it.totalCount()).isEqualTo(2)
            }
        }
    }
}
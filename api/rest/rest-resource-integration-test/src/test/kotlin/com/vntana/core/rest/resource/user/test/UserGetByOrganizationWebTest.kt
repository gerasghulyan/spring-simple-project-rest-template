package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.error.UserErrorResponseModel.NOT_FOUND_FOR_ORGANIZATION
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus.NOT_FOUND

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 5:38 PM
 */
class UserGetByOrganizationWebTest : AbstractUserWebTest() {

    @Test
    fun `test when organization not found`() {
        assertBasicErrorResultResponse(NOT_FOUND, userResourceClient.getUsersByOrganization(uuid()), NOT_FOUND_FOR_ORGANIZATION)
    }

    @Test
    fun `test single user`() {
        val fullName = uuid()
        val email = email()
        val createUserRequest = resourceHelper.buildCreateUserRequest(
                fullName = fullName,
                email = email
        )
        val userUuid = resourceHelper.persistUser(createUserRequest).response().uuid
        val organizationUuid = organizationResourceTestHelper.persistOrganization(userUuid = userUuid).response().uuid
        userResourceClient.getUsersByOrganization(organizationUuid).let { responseEntity ->
            assertBasicSuccessResultResponse(responseEntity)
            responseEntity?.body?.response()?.let {
                assertThat(it.totalCount()).isEqualTo(1)
                assertThat(it.items()[0].email).isEqualTo(email)
                assertThat(it.items()[0].fullName).isEqualTo(fullName)
                assertThat(it.items()[0].userRoleModel).isEqualTo(UserRoleModel.ORGANIZATION_ADMIN)
            }
        }
    }
}
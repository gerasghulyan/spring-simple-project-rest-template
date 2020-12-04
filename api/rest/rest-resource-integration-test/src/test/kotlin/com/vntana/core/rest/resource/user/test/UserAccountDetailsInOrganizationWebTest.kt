package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 04.12.2020
 * Time: 14:07
 */
class UserAccountDetailsInOrganizationWebTest : AbstractUserWebTest() {

    @Test
    fun `test when user not found`() {
        assertBasicErrorResultResponse(userResourceClient.accountDetails(uuid(), uuid()), UserErrorResponseModel.NOT_FOUND_FOR_UUID)
    }

    @Test
    fun `test when organization not found`() {
        val createRequest = resourceHelper.buildCreateUserRequest()
        val user = resourceHelper.persistUser(createRequest).response()
        assertBasicErrorResultResponse(userResourceClient.accountDetails(uuid(), user.uuid), UserErrorResponseModel.NOT_FOUND_FOR_ORGANIZATION)
    }
    
    @Test
    fun test() {
        val createRequest = resourceHelper.buildCreateUserRequest()
        val user = resourceHelper.persistUser(createRequest).response()
        userResourceClient.accountDetails(user.organizationUuid, user.uuid).let {
            assertBasicSuccessResultResponse(it)
            it?.body?.response()?.run {
                assertThat(this.uuid).isEqualTo(user.uuid)
                assertThat(this.email).isEqualTo(createRequest.email)
                assertThat(this.isEmailVerified).isFalse()
                assertThat(this.fullName).isEqualTo(createRequest.fullName)
                assertThat(this.imageBlobId).isNull()
                assertThat(this.userRoleModel).isEqualTo(UserRoleModel.ORGANIZATION_OWNER)
            }
        }
    }
}
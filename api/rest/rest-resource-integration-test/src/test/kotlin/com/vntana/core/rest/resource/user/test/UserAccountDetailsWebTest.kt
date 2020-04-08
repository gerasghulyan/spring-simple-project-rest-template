package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 4/8/20
 * Time: 10:58 AM
 */
class UserAccountDetailsWebTest : AbstractUserWebTest() {

    @Test
    fun `test when not found`() {
        assertBasicErrorResultResponse(userResourceClient.accountDetails(uuid()), UserErrorResponseModel.NOT_FOUND_FOR_UUID)
    }

    @Test
    fun test() {
        val createRequest = resourceHelper.buildCreateUserRequest()
        val response = resourceHelper.persistUser(createRequest).response()
        var organizationUuid = response.organizationUuid
        var userUuid = response.uuid
        userResourceClient.accountDetails(userUuid).let {
            assertBasicSuccessResultResponse(it)
            it?.body?.response()?.run {
                assertThat(this.uuid).isEqualTo(userUuid)
                assertThat(this.email).isEqualTo(createRequest.email)
                assertThat(this.isEmailVerified).isFalse()
                assertThat(this.fullName).isEqualTo(createRequest.fullName)
                assertThat(this.imageBlobId).isNull()
                assertThat(this.roles.superAdmin).isFalse()
                assertThat(this.roles.adminInOrganization).containsOnly(organizationUuid)
            }
        }
    }
}
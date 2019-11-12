package com.vntana.core.rest.resource.client.test

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.rest.resource.client.AbstractClientOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 11/11/2019
 * Time: 9:22 PM
 */
class ClientOrganizationGetClientOrganizationWebTest : AbstractClientOrganizationWebTest() {

    @Test
    fun `test get user client organizations when role is organization admin`() {
        val createUserResponseModel = userResourceTestHelper.persistUser().response()
        val organizationUuid = createUserResponseModel.organizationUuid
        val clientOrganizationName = uuid()
        val clientOrganizationName2 = uuid()
        val clientOrganization = clientOrganizationResourceTestHelper.persistClientOrganization(
                organizationUuid = organizationUuid,
                name = clientOrganizationName)
        val clientOrganization2 = clientOrganizationResourceTestHelper.persistClientOrganization(
                organizationUuid = organizationUuid,
                name = clientOrganizationName2)
        userResourceTestHelper.buildCreateUserRequest()

        clientOrganizationResourceClient.getUserClientOrganizations(createUserResponseModel.uuid, organizationUuid).let {
            assertThat(it.response()).isNotNull
            assertThat(it.response().totalCount()).isEqualTo(2)
            assertThat(it.response().items()[0].uuid).isEqualTo(clientOrganization.response().uuid)
            assertThat(it.response().items()[0].role).isEqualTo(UserRoleModel.ORGANIZATION_ADMIN)
            assertThat(it.response().items()[0].name).isEqualTo(clientOrganizationName)
            assertThat(it.response().items()[1].uuid).isEqualTo(clientOrganization2.response().uuid)
            assertThat(it.response().items()[1].role).isEqualTo(UserRoleModel.ORGANIZATION_ADMIN)
            assertThat(it.response().items()[1].name).isEqualTo(clientOrganizationName2)
        }
    }
}
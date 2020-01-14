package com.vntana.core.rest.resource.client.test

import com.vntana.core.model.client.response.get.GetAllOrganizationsResponseModel
import com.vntana.core.rest.resource.client.AbstractClientOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 1/14/20
 * Time: 5:49 PM
 */
class ClientOrganizationGetAllWebTest : AbstractClientOrganizationWebTest() {
    @Test
    fun `test getAll`() {
        // given
        val organizationName = uuid()
        val organizationUuid = organizationResourceTestHelper.persistOrganization(name = organizationName).response().uuid
        val clientOrganizationName1 = uuid()
        val clientOrganizationName2 = uuid()
        val clientOrganizationUuid1 = clientOrganizationResourceTestHelper
                .persistClientOrganization(organizationUuid = organizationUuid, name = clientOrganizationName1).response().uuid
        val clientOrganizationUuid2 = clientOrganizationResourceTestHelper
                .persistClientOrganization(organizationUuid = organizationUuid, name = clientOrganizationName2).response().uuid
        val models = listOf(
                GetAllOrganizationsResponseModel(organizationUuid, organizationName, clientOrganizationUuid1, clientOrganizationName1),
                GetAllOrganizationsResponseModel(organizationUuid, organizationName, clientOrganizationUuid2, clientOrganizationName2)
        )
        // when
        val response = clientOrganizationResourceClient.all
        // then
        assertBasicSuccessResultResponse(response)
        assertThat(response.response().items()).containsAll(models)
    }
}
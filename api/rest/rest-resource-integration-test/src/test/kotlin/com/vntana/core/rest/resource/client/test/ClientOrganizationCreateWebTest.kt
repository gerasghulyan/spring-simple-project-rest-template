package com.vntana.core.rest.resource.client.test

import com.vntana.core.helper.organization.OrganizationRestTestHelper
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel
import com.vntana.core.rest.resource.client.AbstractClientOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 5:53 PM
 */
class ClientOrganizationCreateWebTest : AbstractClientOrganizationWebTest() {

    private val organizationRestTestHelper = OrganizationRestTestHelper()

    @Test
    fun `test create with invalid arguments`() {
        val response0 = clientOrganizationResourceClient.create(
                restHelper.buildCreateClientOrganizationRequest(organizationUuid = null)
        )
        restHelper.assertBasicErrorResultResponse(response0, ClientOrganizationErrorResponseModel.MISSING_ORGANIZATION_UUID)
        val response1 = clientOrganizationResourceClient.create(
                restHelper.buildCreateClientOrganizationRequest(slug = null)
        )
        restHelper.assertBasicErrorResultResponse(response1, ClientOrganizationErrorResponseModel.MISSING_SLUG)
        val response2 = clientOrganizationResourceClient.create(
                restHelper.buildCreateClientOrganizationRequest(name = null)
        )
        restHelper.assertBasicErrorResultResponse(response2, ClientOrganizationErrorResponseModel.MISSING_NAME)
    }

    @Test
    fun `test create`() {
        val response0 = organizationResourceClient.create(
                organizationRestTestHelper.buildCreateOrganizationRequest()
        )
        val organizationUuid = response0.response().uuid
        val request = restHelper.buildCreateClientOrganizationRequest(organizationUuid = organizationUuid)
        val response = clientOrganizationResourceClient.create(request)
        restHelper.assertBasicSuccessResultResponse(response)
        assertThat(response.response().uuid).isNotBlank()
    }
}
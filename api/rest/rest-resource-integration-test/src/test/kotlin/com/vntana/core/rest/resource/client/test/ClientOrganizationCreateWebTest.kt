package com.vntana.core.rest.resource.client.test

import com.vntana.core.helper.rest.organization.OrganizationRestTestHelper
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

    override fun endpointMapping(): String = baseMapping() + "/create"

    @Test
    fun `test create with invalid arguments`() {
        val response0 = clientOrganizationResourceClient.create(
                restTestHelper.buildCreateClientOrganizationRequest(organizationUuid = null)
        )
        restTestHelper.assertBasicErrorResultResponse(response0, ClientOrganizationErrorResponseModel.MISSING_ORGANIZATION_UUID)
        val response1 = clientOrganizationResourceClient.create(
                restTestHelper.buildCreateClientOrganizationRequest(slug = null)
        )
        restTestHelper.assertBasicErrorResultResponse(response1, ClientOrganizationErrorResponseModel.MISSING_SLUG)
        val response2 = clientOrganizationResourceClient.create(
                restTestHelper.buildCreateClientOrganizationRequest(name = null)
        )
        restTestHelper.assertBasicErrorResultResponse(response2, ClientOrganizationErrorResponseModel.MISSING_NAME)
    }

    @Test
    fun `test create`() {
        val response0 = organizationResourceClient.create(
                organizationRestTestHelper.buildCreateOrganizationRequest()
        )
        val organizationUuid = response0.response().uuid
        val request = restTestHelper.buildCreateClientOrganizationRequest(organizationUuid = organizationUuid)
        val response = clientOrganizationResourceClient.create(request)
        assertThat(response.success()).isTrue()
        assertThat(response.response().uuid).isNotBlank()
    }
}
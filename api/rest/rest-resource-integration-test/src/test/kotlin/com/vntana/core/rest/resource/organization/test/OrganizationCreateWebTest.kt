package com.vntana.core.rest.resource.organization.test

import com.vntana.core.model.organization.error.OrganizationErrorResponseModel
import com.vntana.core.rest.resource.organization.AbstractOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 5:53 PM
 */
class OrganizationCreateWebTest : AbstractOrganizationWebTest() {

    override fun endpointMapping(): String = baseMapping() + "/create"

    @Test
    fun `test create with invalid arguments`() {
        val response1 = organizationResourceClient.create(
                restTestHelper.buildCreateOrganizationRequest(slug = null)
        )
        restTestHelper.assertBasicErrorResultResponse(response1, OrganizationErrorResponseModel.MISSING_SLUG)
        val response2 = organizationResourceClient.create(
                restTestHelper.buildCreateOrganizationRequest(name = null)
        )
        restTestHelper.assertBasicErrorResultResponse(response2, OrganizationErrorResponseModel.MISSING_NAME)
    }

    @Test
    fun `test create`() {
        val request = restTestHelper.buildCreateOrganizationRequest()
        val response = organizationResourceClient.create(request)
        assertThat(response.success()).isTrue()
        assertThat(response.response().uuid).isNotBlank()
    }
}
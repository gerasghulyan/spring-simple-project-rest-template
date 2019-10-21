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
class OrganizationCheckSlugAvailabilityWebTest : AbstractOrganizationWebTest() {
    override fun endpointMapping(): String = baseMapping() + "/slug-availability"

    @Test
    fun `test checkSlugAvailability when slug is missing`() {
        val request = restTestHelper.buildCheckAvailableOrganizationSlugRequest(slug = null)
        val response = organizationResourceClient.checkSlugAvailability(request)
        restTestHelper.assertBasicErrorResultResponse(response, OrganizationErrorResponseModel.MISSING_SLUG)
    }

    @Test
    fun `test checkSlugAvailability`() {
        val request = restTestHelper.buildCheckAvailableOrganizationSlugRequest()
        val response = organizationResourceClient.checkSlugAvailability(request)
        assertThat(response.success()).isTrue()
        assertThat(response.response().isAvailable).isTrue()
        assertThat(response.response().suggested).isEqualTo(request.slug)
    }
}
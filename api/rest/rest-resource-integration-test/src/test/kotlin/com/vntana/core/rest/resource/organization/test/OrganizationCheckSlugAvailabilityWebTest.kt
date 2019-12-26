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
    @Test
    fun `test checkSlugAvailability with invalid arguments`() {
        val request = resourceTestHelper.buildCheckAvailableOrganizationSlugRequest(slug = null)
        val response = organizationResourceClient.checkSlugAvailability(request)
        assertBasicErrorResultResponse(response, OrganizationErrorResponseModel.MISSING_SLUG)
    }

    @Test
    fun `test checkSlugAvailability`() {
        val request = resourceTestHelper.buildCheckAvailableOrganizationSlugRequest()
        val response = organizationResourceClient.checkSlugAvailability(request)
        assertBasicSuccessResultResponse(response)
        assertThat(response.response().isAvailable).isTrue()
        assertThat(response.response().suggested).isEqualTo(request.slug)
    }
}
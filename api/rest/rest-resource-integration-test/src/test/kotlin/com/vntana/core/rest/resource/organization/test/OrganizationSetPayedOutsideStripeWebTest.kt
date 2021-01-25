package com.vntana.core.rest.resource.organization.test

import com.vntana.core.model.organization.error.OrganizationErrorResponseModel
import com.vntana.core.model.organization.request.OrganizationPayedOutsideStripeRequest
import com.vntana.core.rest.resource.organization.AbstractOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 1/26/21
 * Time: 1:23 AM
 */
class OrganizationSetPayedOutsideStripeWebTest : AbstractOrganizationWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
            organizationResourceClient.setPaymentOutsideStripe(
                OrganizationPayedOutsideStripeRequest(null, true)
            ),
            OrganizationErrorResponseModel.MISSING_UUID
        )
    }

    @Test
    fun `test when organization not found`() {
        assertBasicErrorResultResponse(
            organizationResourceClient.setPaymentOutsideStripe(
                OrganizationPayedOutsideStripeRequest(uuid(), true)
            ),
            OrganizationErrorResponseModel.ORGANIZATION_NOT_FOUND
        )
    }

    @Test
    fun test() {
        val organizationUuid = resourceTestHelper.persistOrganization().response().uuid
        val isPayedOutsideStripe = true
        organizationResourceClient.setPaymentOutsideStripe(
            OrganizationPayedOutsideStripeRequest(organizationUuid, isPayedOutsideStripe)
        ).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body?.response()?.uuid).isEqualTo(organizationUuid)
            assertThat(it.body?.response()?.isPayedOutsideStripe).isEqualTo(isPayedOutsideStripe)
        }
    }
}
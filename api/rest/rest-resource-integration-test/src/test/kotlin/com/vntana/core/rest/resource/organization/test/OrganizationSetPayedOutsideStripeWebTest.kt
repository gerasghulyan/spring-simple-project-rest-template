package com.vntana.core.rest.resource.organization.test

import com.vntana.core.model.organization.error.OrganizationErrorResponseModel
import com.vntana.core.model.organization.request.OrganizationPaidOutsideStripeRequest
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
                OrganizationPaidOutsideStripeRequest(
                    null,
                    true
                )
            ),
            OrganizationErrorResponseModel.MISSING_UUID
        )
    }

    @Test
    fun `test when organization not found`() {
        assertBasicErrorResultResponse(
            organizationResourceClient.setPaymentOutsideStripe(
                OrganizationPaidOutsideStripeRequest(
                    uuid(),
                    true
                )
            ),
            OrganizationErrorResponseModel.ORGANIZATION_NOT_FOUND
        )
    }

    @Test
    fun `test when payed outside stripe is false`() {
        val organizationUuid = resourceTestHelper.persistOrganization().response().uuid
        val isPaidOutsideStripe = false
        organizationResourceClient.setPaymentOutsideStripe(
            OrganizationPaidOutsideStripeRequest(
                organizationUuid,
                isPaidOutsideStripe
            )
        ).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body?.response()?.uuid).isEqualTo(organizationUuid)
            assertThat(it.body?.response()?.isPaidOutsideStripe).isEqualTo(isPaidOutsideStripe)
        }
    }

    @Test
    fun `test when payed outside stripe is true`() {
        val organizationUuid = resourceTestHelper.persistOrganization().response().uuid
        val isPaidOutsideStripe = true
        organizationResourceClient.setPaymentOutsideStripe(
            OrganizationPaidOutsideStripeRequest(
                organizationUuid,
                isPaidOutsideStripe
            )
        ).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body?.response()?.uuid).isEqualTo(organizationUuid)
            assertThat(it.body?.response()?.isPaidOutsideStripe).isEqualTo(isPaidOutsideStripe)
        }
    }
}
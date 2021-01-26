package com.vntana.core.rest.resource.organization.test

import com.vntana.core.model.organization.error.OrganizationErrorResponseModel
import com.vntana.core.rest.resource.organization.AbstractOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 1/26/21
 * Time: 2:03 AM
 */
class OrganizationGetPayedOutsideStripeWebTest : AbstractOrganizationWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
            organizationResourceClient.getPaymentOutsideStripe(
                null
            ),
            OrganizationErrorResponseModel.ORGANIZATION_NOT_FOUND
        )
    }

    @Test
    fun `test when organization not found`() {
        assertBasicErrorResultResponse(
            organizationResourceClient.getPaymentOutsideStripe(
                uuid()
            ),
            OrganizationErrorResponseModel.ORGANIZATION_NOT_FOUND
        )
    }

    @Test
    fun test() {
        val organizationUuid = resourceTestHelper.persistOrganization().response().uuid
        val isPayedOutsideStripe = false
        organizationResourceClient.getPaymentOutsideStripe(
            organizationUuid
        ).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body?.response()?.uuid).isEqualTo(organizationUuid)
            assertThat(it.body?.response()?.isPayedOutsideStripe).isEqualTo(isPayedOutsideStripe)
        }
    }
}
package com.vntana.core.service.organization.impl

import com.vntana.core.service.organization.AbstractOrganizationServiceIntegrationTest
import com.vntana.core.service.organization.dto.OrganizationPayedOutsideStripeDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 1/26/21
 * Time: 12:16 AM
 */
class OrganizationSetPaymentOutsideStripeIntegrationTest : AbstractOrganizationServiceIntegrationTest() {

    @Test
    fun `test when payedOutsideFlag is true`() {
        // given
        val organization = integrationTestHelper.persistOrganization()
        val isPayedOutsideStripe = true
        val updateDto = OrganizationPayedOutsideStripeDto(organization.uuid, isPayedOutsideStripe)
        flushAndClear()
        // when
        organizationService.setPaymentOutsideStripe(updateDto).let {
            // then
            flushAndClear()
            assertThat(it.uuid).isEqualTo(organization.uuid)
            assertThat(it.isPayedOutsideStripe).isEqualTo(isPayedOutsideStripe)
        }
    }

    @Test
    fun `test when payedOutsideFlag is false`() {
        // given
        val organization = integrationTestHelper.persistOrganization()
        val isPayedOutsideStripe = false
        val updateDto = OrganizationPayedOutsideStripeDto(organization.uuid, isPayedOutsideStripe)
        flushAndClear()
        // when
        organizationService.setPaymentOutsideStripe(updateDto).let {
            // then
            flushAndClear()
            assertThat(it.uuid).isEqualTo(organization.uuid)
            assertThat(it.isPayedOutsideStripe).isEqualTo(isPayedOutsideStripe)
        }
    }
}
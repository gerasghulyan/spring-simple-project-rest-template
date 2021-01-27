package com.vntana.core.service.organization.impl

import com.vntana.core.service.organization.AbstractOrganizationServiceUnitTest
import com.vntana.core.service.organization.dto.OrganizationPayedOutsideStripeDto
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Diana Gevorgyan
 * Date: 1/26/21
 * Time: 12:28 AM
 */
class OrganizationSetPaymentOutsideStripeServiceUnitTest : AbstractOrganizationServiceUnitTest() {

    @Test
    fun `test get with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { organizationService.setPaymentOutsideStripe(null) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy {
            organizationService.setPaymentOutsideStripe(OrganizationPayedOutsideStripeDto(null, false))
        }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `set organization payment outside stripe`() {
        // test data
        val organization = helper.buildOrganization()
        val isPayedOutsideStripe = true
        val updatedOrganization = organization
        updatedOrganization.isPayedOutsideStripe = isPayedOutsideStripe
        resetAll()
        // expectations
        expect(
            organizationRepository.findByUuid(organization.uuid)
        ).andReturn(Optional.of(organization))
        expect(organizationRepository.save(organization)).andReturn(updatedOrganization)
        replayAll()
        // test scenario
        organizationService.setPaymentOutsideStripe(
            OrganizationPayedOutsideStripeDto(
                organization.uuid,
                isPayedOutsideStripe
            )
        )
            .let {
                assertThat(it.uuid).isEqualTo(organization.uuid)
                assertThat(it.isPayedOutsideStripe).isEqualTo(isPayedOutsideStripe)
            }
        verifyAll()
    }
}
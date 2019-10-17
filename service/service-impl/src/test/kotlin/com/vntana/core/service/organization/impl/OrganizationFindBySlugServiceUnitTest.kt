package com.vntana.core.service.organization.impl

import com.vntana.core.service.organization.AbstractOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 12:12 PM
 */
class OrganizationFindBySlugServiceUnitTest : AbstractOrganizationServiceUnitTest() {
    @Test
    fun `test findBySlug with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { organizationService.findBySlug(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationService.findBySlug(" ") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test findBySlug when not found`() {
        // test data
        resetAll()
        val slug = uuid()
        // expectations
        expect(organizationRepository.findBySlug(slug)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        assertThat(organizationRepository.findBySlug(slug)).isEmpty
        verifyAll()
    }

    @Test
    fun `test findBySlug when found`() {
        // test data
        resetAll()
        val slug = uuid()
        val organization = helper.buildOrganization()
        // expectations
        expect(organizationRepository.findBySlug(slug)).andReturn(Optional.of(organization))
        replayAll()
        // test scenario
        assertThat(organizationService.findBySlug(slug)).isNotEmpty.hasValue(organization)
        verifyAll()
    }
}
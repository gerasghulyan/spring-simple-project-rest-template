package com.vntana.core.service.client.impl

import com.vntana.core.service.client.AbstractClientOrganizationServiceUnitTest
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
class ClientOrganizationFindBySlugAndOrganizationServiceUnitTest : AbstractClientOrganizationServiceUnitTest() {
    @Test
    fun `test findBySlug with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { clientOrganizationService.findBySlugAndOrganization(null, uuid()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { clientOrganizationService.findBySlugAndOrganization("", uuid()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { clientOrganizationService.findBySlugAndOrganization(uuid(), null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { clientOrganizationService.findBySlugAndOrganization(uuid(), emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test findBySlug when not found`() {
        // test data
        resetAll()
        val slug = uuid()
        val organizationUuid = uuid()
        // expectations
        expect(clientOrganizationRepository.findBySlugAndOrganizationUuid(slug, organizationUuid)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        assertThat(clientOrganizationService.findBySlugAndOrganization(slug, organizationUuid)).isEmpty
        verifyAll()
    }

    @Test
    fun `test findBySlug when found`() {
        // test data
        resetAll()
        val clientOrganization = helper.buildClientOrganization()
        val slug = clientOrganization.slug
        val organization = clientOrganization.organization
        // expectations
        expect(clientOrganizationRepository.findBySlugAndOrganizationUuid(slug, organization.uuid)).andReturn(Optional.of(clientOrganization))
        replayAll()
        // test scenario
        assertThat(clientOrganizationService.findBySlugAndOrganization(slug, organization.uuid)).isNotEmpty.hasValue(clientOrganization)
        verifyAll()
    }
}
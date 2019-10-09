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
class ClientOrganizationFindBySlugServiceUnitTest : AbstractClientOrganizationServiceUnitTest() {
    @Test
    fun `test findBySlug with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { clientOrganizationService.findBySlug(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { clientOrganizationService.findBySlug(" ") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test findBySlug when not found`() {
        // test data
        resetAll()
        val slug = uuid()
        // expectations
        expect(clientOrganizationRepository.findBySlug(slug)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        assertThat(clientOrganizationService.findBySlug(slug)).isEmpty
        verifyAll()
    }

    @Test
    fun `test findBySlug when found`() {
        // test data
        resetAll()
        val slug = uuid()
        val clientOrganization = helper.buildClientOrganization()
        // expectations
        expect(clientOrganizationRepository.findBySlug(slug)).andReturn(Optional.of(clientOrganization))
        replayAll()
        // test scenario
        assertThat(clientOrganizationService.findBySlug(slug)).isNotEmpty.hasValue(clientOrganization)
        verifyAll()
    }
}
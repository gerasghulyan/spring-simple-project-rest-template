package com.bntana.core.service.client.impl

import com.bntana.core.service.client.AbstractClientOrganizationServiceUnitTest
import com.vntana.core.domain.client.ClientOrganization
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 6:04 PM
 */
class CreateClientOrganizationServiceUnitTest : AbstractClientOrganizationServiceUnitTest() {
    @Test
    fun `test create with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { clientOrganizationService.create(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { clientOrganizationService.create(helper.buildCreateClientOrganizationDto(name = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { clientOrganizationService.create(helper.buildCreateClientOrganizationDto(name = " ")) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { clientOrganizationService.create(helper.buildCreateClientOrganizationDto(slug = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { clientOrganizationService.create(helper.buildCreateClientOrganizationDto(slug = " ")) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test create when client with slug already exists`() {
        // test data
        resetAll()
        val slug = uuid()
        val clientOrganization = helper.buildClientOrganization()
        // expectations
        expect(clientOrganizationRepository.findBySlug(slug)).andReturn(Optional.of(clientOrganization))
        replayAll()
        // test scenario
        assertThatThrownBy { clientOrganizationService.create(helper.buildCreateClientOrganizationDto(slug = slug)) }
                .isExactlyInstanceOf(IllegalStateException::class.java)
        verifyAll()
    }

    @Test
    fun `test create`() {
        // test data
        resetAll()
        val slug = uuid()
        val dto = helper.buildCreateClientOrganizationDto(slug = slug)
        // expectations
        expect(clientOrganizationRepository.findBySlug(slug)).andReturn(Optional.empty())
        expect(clientOrganizationRepository.save(isA(ClientOrganization::class.java)))
                .andAnswer { getCurrentArguments()[0] as ClientOrganization }
        replayAll()
        // test scenario
        assertThat(clientOrganizationService.create(dto))
                .hasFieldOrPropertyWithValue("name", dto.name)
                .hasFieldOrPropertyWithValue("slug", dto.slug)
        verifyAll()
    }
}
package com.vntana.core.service.client.impl

import com.vntana.core.domain.client.ClientOrganization
import com.vntana.core.service.client.AbstractClientOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan.
 * Date: 10/8/19
 * Time: 6:04 PM
 */
class ClientOrganizationUpdateServiceUnitTest : AbstractClientOrganizationServiceUnitTest() {
    @Test
    fun `test with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { clientOrganizationService.update(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { clientOrganizationService.update(helper.buildUpdateClientOrganizationDto(uuid = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { clientOrganizationService.update(helper.buildUpdateClientOrganizationDto(uuid = "")) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { clientOrganizationService.update(helper.buildUpdateClientOrganizationDto(name = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { clientOrganizationService.update(helper.buildUpdateClientOrganizationDto(name = "")) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { clientOrganizationService.update(helper.buildUpdateClientOrganizationDto(slug = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { clientOrganizationService.update(helper.buildUpdateClientOrganizationDto(slug = "")) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when client with slug already exists`() {
        // test data
        resetAll()
        val clientOrganization = helper.buildClientOrganization()
        val clientOrganization2 = helper.buildClientOrganization()
        val slug = clientOrganization.slug
        val organizationUuid = clientOrganization.organization.uuid
        val dto = helper.buildUpdateClientOrganizationDto(slug = slug, uuid = clientOrganization.uuid)
        // expectations
        expect(clientOrganizationRepository.findByUuid(clientOrganization.uuid)).andReturn(Optional.of(clientOrganization))
        expect(clientOrganizationRepository.findBySlugAndOrganizationUuid(slug, organizationUuid)).andReturn(Optional.of(clientOrganization2))
        replayAll()
        // test scenario
        assertThatThrownBy { clientOrganizationService.update(dto) }
                .isExactlyInstanceOf(IllegalStateException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        // test data
        resetAll()
        val slug = uuid()
        val imageId = uuid()
        val clientOrganization = helper.buildClientOrganization()
        val dto = helper.buildUpdateClientOrganizationDto(slug = slug, imageId = imageId, uuid = clientOrganization.uuid)
        // expectations
        expect(clientOrganizationRepository.findByUuid(clientOrganization.uuid)).andReturn(Optional.of(clientOrganization))
        expect(clientOrganizationRepository.findBySlugAndOrganizationUuid(slug, clientOrganization.organization.uuid)).andReturn(Optional.of(clientOrganization))
        expect(clientOrganizationRepository.save(isA(ClientOrganization::class.java)))
                .andAnswer { getCurrentArguments()[0] as ClientOrganization }
        replayAll()
        // test scenario
        assertThat(clientOrganizationService.update(dto))
                .hasFieldOrPropertyWithValue("name", dto.name)
                .hasFieldOrPropertyWithValue("slug", dto.slug)
                .hasFieldOrPropertyWithValue("imageId", dto.imageId)
                .hasFieldOrPropertyWithValue("organization", clientOrganization.organization)
                .hasFieldOrPropertyWithValue("uuid", dto.uuid)
        verifyAll()
    }
}
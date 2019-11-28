package com.vntana.core.service.client.impl

import com.vntana.core.domain.client.ClientOrganization
import com.vntana.core.service.client.AbstractClientOrganizationServiceUnitTest
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
class ClientOrganizationCreateServiceUnitTest : AbstractClientOrganizationServiceUnitTest() {
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
        assertThatThrownBy { clientOrganizationService.create(helper.buildCreateClientOrganizationDto(imageId = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { clientOrganizationService.create(helper.buildCreateClientOrganizationDto(imageId = " ")) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test create when client with slug already exists`() {
        // test data
        resetAll()
        val clientOrganization = helper.buildClientOrganization()
        val slug = clientOrganization.slug
        val organizationUuid = clientOrganization.organization.uuid
        val dto = helper.buildCreateClientOrganizationDto(slug = slug, organizationUuid = organizationUuid)
        // expectations
        expect(clientOrganizationRepository.findBySlugAndOrganizationUuid(slug, organizationUuid)).andReturn(Optional.of(clientOrganization))
        replayAll()
        // test scenario
        assertThatThrownBy { clientOrganizationService.create(dto) }
                .isExactlyInstanceOf(IllegalStateException::class.java)
        verifyAll()
    }

    @Test
    fun `test create`() {
        // test data
        resetAll()
        val slug = uuid()
        val imageId = uuid()
        val organization = organizationTestHelper.buildOrganization()
        val dto = helper.buildCreateClientOrganizationDto(slug = slug, imageId = imageId, organizationUuid = organization.uuid)
        // expectations
        expect(clientOrganizationRepository.findBySlugAndOrganizationUuid(slug, organization.uuid)).andReturn(Optional.empty())
        expect(organizationService.getByUuid(organization.uuid)).andReturn(organization)
        expect(clientOrganizationRepository.save(isA(ClientOrganization::class.java)))
                .andAnswer { getCurrentArguments()[0] as ClientOrganization }
        replayAll()
        // test scenario
        assertThat(clientOrganizationService.create(dto))
                .hasFieldOrPropertyWithValue("name", dto.name)
                .hasFieldOrPropertyWithValue("slug", dto.slug)
                .hasFieldOrPropertyWithValue("imageId", dto.imageId)
                .hasFieldOrPropertyWithValue("organization", organization)
        verifyAll()
    }
}
package com.vntana.core.service.organization.impl

import com.vntana.core.domain.organization.Organization
import com.vntana.core.service.organization.AbstractOrganizationServiceUnitTest
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
class OrganizationCreateServiceUnitTest : AbstractOrganizationServiceUnitTest() {
    @Test
    fun `test create with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { organizationService.create(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationService.create(helper.buildCreateOrganizationDto(name = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationService.create(helper.buildCreateOrganizationDto(name = " ")) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationService.create(helper.buildCreateOrganizationDto(slug = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationService.create(helper.buildCreateOrganizationDto(slug = " ")) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test create when organization with slug already exists`() {
        // test data
        resetAll()
        val slug = uuid()
        val organization = helper.buildOrganization()
        // expectations
        expect(organizationRepository.findBySlug(slug)).andReturn(Optional.of(organization))
        replayAll()
        // test scenario
        assertThatThrownBy { organizationService.create(helper.buildCreateOrganizationDto(slug = slug)) }
                .isExactlyInstanceOf(IllegalStateException::class.java)
        verifyAll()
    }

    @Test
    fun `test create`() {
        // test data
        resetAll()
        val slug = uuid()
        val dto = helper.buildCreateOrganizationDto(slug = slug)
        // expectations
        expect(organizationRepository.findBySlug(slug)).andReturn(Optional.empty())
        expect(organizationRepository.save(isA(Organization::class.java)))
                .andAnswer { getCurrentArguments()[0] as Organization }
        replayAll()
        // test scenario
        assertThat(organizationService.create(dto))
                .hasFieldOrPropertyWithValue("name", dto.name)
                .hasFieldOrPropertyWithValue("slug", dto.slug)
        verifyAll()
    }
}
package com.vntana.core.service.organization.impl

import com.vntana.core.domain.organization.Organization
import com.vntana.core.service.organization.AbstractOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 3:45 PM
 */
class OrganizationUpdateServiceUnitTest : AbstractOrganizationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { helper.buildUpdateOrganizationDto(uuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { helper.buildUpdateOrganizationDto(uuid = "") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { helper.buildUpdateOrganizationDto(name = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { helper.buildUpdateOrganizationDto(name = "") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationService.update(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        val dto = helper.buildUpdateOrganizationDto()
        resetAll()
        expect(organizationRepository.findByUuid(dto.uuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy {organizationService.update(dto)}
                .isExactlyInstanceOf(IllegalStateException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        val organizationSlug = uuid()
        val organization = helper.buildOrganization(slug = organizationSlug)
        val dto = helper.buildUpdateOrganizationDto()
        resetAll()
        expect(organizationRepository.findByUuid(dto.uuid)).andReturn(Optional.of(organization))
        expect(organizationRepository.save(isA(Organization::class.java)))
                .andAnswer { getCurrentArguments()[0] as Organization }
        replayAll()
        organizationService.update(dto).let {
            assertThat(it.imageBlobId).isEqualTo(dto.imageBlobId)
            assertThat(it.name).isEqualTo(dto.name)
            assertThat(it.slug).isEqualTo(organizationSlug)
        }
        verifyAll()
    }
}
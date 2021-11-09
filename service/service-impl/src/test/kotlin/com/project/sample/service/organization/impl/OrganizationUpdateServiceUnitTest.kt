package com.project.sample.service.organization.impl

import com.project.sample.commons.persistence.domain.organization.Organization
import com.project.sample.service.organization.AbstractOrganizationServiceUnitTest
import com.project.sample.service.organization.exception.OrganizationNotFoundForUuidException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan.
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
        assertThatThrownBy { helper.buildUpdateOrganizationDto(uuid = emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { helper.buildUpdateOrganizationDto(name = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { helper.buildUpdateOrganizationDto(name = emptyString()) }
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
        assertThatThrownBy { organizationService.update(dto) }
                .isExactlyInstanceOf(OrganizationNotFoundForUuidException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        val organization = helper.buildOrganization()
        val dto = helper.buildUpdateOrganizationDto()
        resetAll()
        expect(organizationRepository.findByUuid(dto.uuid)).andReturn(Optional.of(organization))
        expect(organizationRepository.save(isA(Organization::class.java)))
                .andAnswer { getCurrentArguments()[0] as Organization }
        replayAll()
        organizationService.update(dto).let {
            assertThat(it.name).isEqualTo(dto.name)
            assertThat(it.status).isEqualTo(dto.status.get())
        }
        verifyAll()
    }

    @Test
    fun `test update without status`() {
        val organization = helper.buildOrganization()
        val dto = helper.buildUpdateOrganizationDto(status = null)
        resetAll()
        expect(organizationRepository.findByUuid(dto.uuid)).andReturn(Optional.of(organization))
        expect(organizationRepository.save(isA(Organization::class.java)))
                .andAnswer { getCurrentArguments()[0] as Organization }
        replayAll()
        organizationService.update(dto).let {
            assertThat(it.name).isEqualTo(dto.name)
            assertThat(it.status).isEqualTo(organization.status)
        }
        verifyAll()
    }
}
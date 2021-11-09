package com.project.sample.service.organization.impl

import com.project.sample.commons.persistence.domain.organization.Organization
import com.project.sample.service.organization.AbstractOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test

/**
 * Created by Geras Ghulyan.
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
        assertThatThrownBy { organizationService.create(helper.buildCreateOrganizationDto(name = emptyString())) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test create`() {
        // test data
        resetAll()
        val dto = helper.buildCreateOrganizationDto()
        // expectations
        expect(organizationRepository.save(isA(Organization::class.java)))
            .andAnswer { getCurrentArguments()[0] as Organization }
        replayAll()
        // test scenario
        assertThat(organizationService.create(dto))
            .hasFieldOrPropertyWithValue("name", dto.name)
        verifyAll()
    }
}
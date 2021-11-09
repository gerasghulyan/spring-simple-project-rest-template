package com.project.sample.service.organization.impl

import com.project.sample.service.organization.AbstractOrganizationServiceUnitTest
import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan.
 * Date: 10/9/19
 * Time: 12:07 PM
 */
class OrganizationFindByUuidServiceUnitTest : AbstractOrganizationServiceUnitTest() {
    @Test
    fun `test findByUuid with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { organizationService.findByUuid(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationService.findByUuid(StringUtils.EMPTY) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test findByUuid when not found`() {
        // test data
        resetAll()
        val uuid = uuid()
        // expectations
        expect(organizationRepository.findByUuid(uuid)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        organizationService.findByUuid(uuid).let { 
            assertThat(it.isPresent).isFalse()
        }
        verifyAll()
    }

    @Test
    fun `test findByUuid when found`() {
        // test data
        resetAll()
        val uuid = uuid()
        val clientOrganization = helper.buildOrganization()
        // expectations
        expect(organizationRepository.findByUuid(uuid)).andReturn(Optional.of(clientOrganization))
        replayAll()
        // test scenario
        organizationService.findByUuid(uuid).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.get()).isEqualTo(clientOrganization)
        }
        verifyAll()
    }
}
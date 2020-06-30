package com.vntana.core.service.organization.impl

import com.vntana.core.service.organization.AbstractOrganizationServiceUnitTest
import com.vntana.core.service.organization.exception.OrganizationNotFoundForUuidException
import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 12:07 PM
 */
class OrganizationGetByUuidServiceUnitTest : AbstractOrganizationServiceUnitTest() {
    @Test
    fun `test findByUuid with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { organizationService.getByUuid(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationService.getByUuid(StringUtils.EMPTY) }
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
        assertThatThrownBy { organizationService.getByUuid(uuid) }
                .isExactlyInstanceOf(OrganizationNotFoundForUuidException::class.java)
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
        organizationService.getByUuid(uuid).let {
            assertThat(it).isEqualTo(clientOrganization)
        }
        verifyAll()
    }
}
package com.vntana.core.service.organization.impl

import com.vntana.core.service.organization.AbstractOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 12/28/19
 * Time: 12:26 PM
 */
class OrganizationGetOrganizationOwnerEmailServiceUnitTest : AbstractOrganizationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { organizationService.getOrganizationOwnerEmail(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationService.getOrganizationOwnerEmail("") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        val organizationUuid = uuid()
        resetAll()
        expect(organizationRepository.findOrganizationOwnerEmail(organizationUuid)).andReturn(Optional.empty())
        replayAll()
        assertThat(organizationService.getOrganizationOwnerEmail(organizationUuid)).isEqualTo("")
        verifyAll()
    }

    @Test
    fun `test when found`() {
        val organizationUuid = uuid()
        val email = uuid()
        resetAll()
        expect(organizationRepository.findOrganizationOwnerEmail(organizationUuid)).andReturn(Optional.of(email))
        replayAll()
        assertThat(organizationService.getOrganizationOwnerEmail(organizationUuid)).isEqualTo(email)
        verifyAll()
    }
}
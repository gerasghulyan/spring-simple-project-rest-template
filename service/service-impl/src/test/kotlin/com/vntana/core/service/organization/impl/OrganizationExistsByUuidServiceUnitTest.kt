package com.vntana.core.service.organization.impl

import com.vntana.core.service.organization.AbstractOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 6:04 PM
 */
class OrganizationExistsByUuidServiceUnitTest : AbstractOrganizationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { organizationService.existsByUuid(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationService.existsByUuid("") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        val uuid = uuid()
        resetAll()
        expect(organizationRepository.existsByUuid(uuid)).andReturn(false)
        replayAll()
        assertThat(organizationService.existsByUuid(uuid)).isFalse()
        verifyAll()
    }

    @Test
    fun `test when found`() {
        val uuid = uuid()
        resetAll()
        expect(organizationRepository.existsByUuid(uuid)).andReturn(true)
        replayAll()
        assertThat(organizationService.existsByUuid(uuid)).isTrue()
        verifyAll()
    }
}
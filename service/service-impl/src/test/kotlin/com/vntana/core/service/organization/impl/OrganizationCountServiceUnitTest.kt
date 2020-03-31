package com.vntana.core.service.organization.impl

import com.vntana.core.service.organization.AbstractOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 3/24/2020
 * Time: 3:50 PM
 */
class OrganizationCountServiceUnitTest : AbstractOrganizationServiceUnitTest() {

    @Test
    fun `test count not found`() {
        resetAll()
        expect(organizationRepository.count()).andReturn(0L)
        replayAll()
        assertThat(organizationService.count()).isEqualTo(0L)
        verifyAll()
    }

    @Test
    fun `test count`() {
        resetAll()
        expect(organizationRepository.count()).andReturn(10L)
        replayAll()
        assertThat(organizationService.count()).isEqualTo(10L)
        verifyAll()
    }
}
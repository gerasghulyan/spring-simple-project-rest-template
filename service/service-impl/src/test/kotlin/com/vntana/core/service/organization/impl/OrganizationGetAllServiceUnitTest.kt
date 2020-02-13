package com.vntana.core.service.organization.impl

import com.vntana.core.service.organization.AbstractOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 2/13/20
 * Time: 2:32 PM
 */
class OrganizationGetAllServiceUnitTest : AbstractOrganizationServiceUnitTest() {

    @Test
    fun test() {
        val organizations = listOf( helper.buildOrganization(), helper.buildOrganization())
        resetAll()
        expect(organizationRepository.findAll()).andReturn(organizations)
        replayAll()
        assertThat(organizationService.all).containsOnlyElementsOf(organizations)
        verifyAll()
    }
}
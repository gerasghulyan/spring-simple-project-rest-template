package com.vntana.core.rest.facade.client.impl

import com.vntana.core.rest.facade.client.AbstractClientOrganizationServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 1/14/20
 * Time: 5:41 PM
 */
class ClientOrganizationGetAllServiceFacadeUnitTest : AbstractClientOrganizationServiceFacadeUnitTest() {
    @Test
    fun `test getAll`() {
        resetAll()
        val organizations = listOf(organizationCommonTestHelper.buildOrganization())
        expect(organizationService.all).andReturn(organizations)
        replayAll()
        assertThat(clientOrganizationServiceFacade.all.response().items()).isEmpty()
        verifyAll()
    }
}
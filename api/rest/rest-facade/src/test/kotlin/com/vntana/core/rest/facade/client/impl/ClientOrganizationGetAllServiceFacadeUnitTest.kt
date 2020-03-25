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
        val organizations = listOf(organizationCommonTestHelper.buildOrganization())
        resetAll()
        expect(organizationService.count()).andReturn(organizations.size.toLong())
        expect(organizationService.getAll(organizationCommonTestHelper.buildGetAllOrganizationDto(size = organizations.size))).andReturn(organizationCommonTestHelper.buildOrganizationPage(
                organizations = organizations,
                pageAble = organizationCommonTestHelper.buildPageRequest(size = organizations.size)
        ))
        replayAll()
        assertThat(clientOrganizationServiceFacade.all.response().items()).isEmpty()
        verifyAll()
    }
}
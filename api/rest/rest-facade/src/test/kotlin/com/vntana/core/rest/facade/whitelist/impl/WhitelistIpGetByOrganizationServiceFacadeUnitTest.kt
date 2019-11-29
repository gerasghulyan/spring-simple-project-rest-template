package com.vntana.core.rest.facade.whitelist.impl

import com.vntana.core.model.whitelist.response.model.GetWhitelistIpResponseModel
import com.vntana.core.rest.facade.whitelist.AbstractWhitelistIpServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 11/29/19
 * Time: 4:54 PM
 */
class WhitelistIpGetByOrganizationServiceFacadeUnitTest : AbstractWhitelistIpServiceFacadeUnitTest() {

    @Test
    fun `test when organization not found`() {
        val organizationUuid = uuid()
        val organization = organizationCommonTestHelper.buildOrganization()
        val whitelistIp = commonTestHelper.buildWhitelistIp(organization = organization)
        val whitelistIps = listOf(whitelistIp)
        val responseItem = testHelper.buildGetWhitelistIpResponseModel(organizationUuid = organizationUuid, label = whitelistIp.label, ip = whitelistIp.ip)
        resetAll()
        expect(whitelistIpService.getByOrganization(organizationUuid)).andReturn(whitelistIps)
        expect(mapperFacade.mapAsList(whitelistIps, GetWhitelistIpResponseModel::class.java)).andReturn(listOf(responseItem))
        replayAll()
        whitelistIpServiceFacade.getByOrganization(organizationUuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().totalCount()).isEqualTo(1)
            assertThat(it.response().items()[0].organizationUuid).isEqualTo(organizationUuid)
            assertThat(it.response().items()[0].label).isEqualTo(whitelistIp.label)
            assertThat(it.response().items()[0].ip).isEqualTo(whitelistIp.ip)
        }
        verifyAll()
    }
}
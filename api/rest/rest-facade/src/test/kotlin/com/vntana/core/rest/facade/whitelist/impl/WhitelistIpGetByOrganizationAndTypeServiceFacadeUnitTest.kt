package com.vntana.core.rest.facade.whitelist.impl

import com.vntana.core.domain.whitelist.WhitelistIp
import com.vntana.core.domain.whitelist.WhitelistType
import com.vntana.core.model.whitelist.request.WhitelistTypeModel
import com.vntana.core.model.whitelist.response.model.GetWhitelistIpResponseModel
import com.vntana.core.rest.facade.whitelist.AbstractWhitelistIpServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.eq
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 11/29/19
 * Time: 4:54 PM
 */
class WhitelistIpGetByOrganizationAndTypeServiceFacadeUnitTest : AbstractWhitelistIpServiceFacadeUnitTest() {

    @Test
    fun `test when no ips found`() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val type = WhitelistType.EMBEDDED
        val request = testHelper.buildGetWhitelistIpsRequest(
            organizationUuid = organization.uuid,
            type = WhitelistTypeModel.valueOf(type.name)
        )
        val whitelistIps = emptyList<WhitelistIp>()
        resetAll()
        expect(whitelistIpService.getByOrganizationAndType(eq(organization.uuid), eq(type))).andReturn(whitelistIps)
        expect(mapperFacade.mapAsList(whitelistIps, GetWhitelistIpResponseModel::class.java)).andReturn(emptyList())
        replayAll()
        whitelistIpServiceFacade.getByOrganizationAndType(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().totalCount()).isEqualTo(0)
        }
        verifyAll()
    }

    @Test
    fun test() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val type = WhitelistType.EMBEDDED
        val request = testHelper.buildGetWhitelistIpsRequest(
            organizationUuid = organization.uuid,
            type = WhitelistTypeModel.valueOf(type.name)
        )
        val whitelistIp = commonTestHelper.buildWhitelistIp(organization = organization)
        val whitelistIps = listOf(whitelistIp)
        val responseItem = testHelper.buildGetWhitelistIpResponseModel(
            organizationUuid = organization.uuid,
            label = whitelistIp.label,
            ip = whitelistIp.ip
        )
        resetAll()
        expect(whitelistIpService.getByOrganizationAndType(eq(organization.uuid), eq(type))).andReturn(whitelistIps)
        expect(mapperFacade.mapAsList(whitelistIps, GetWhitelistIpResponseModel::class.java)).andReturn(
            listOf(
                responseItem
            )
        )
        replayAll()
        whitelistIpServiceFacade.getByOrganizationAndType(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().totalCount()).isEqualTo(1)
            assertThat(it.response().items()[0].organizationUuid).isEqualTo(organization.uuid)
            assertThat(it.response().items()[0].label).isEqualTo(whitelistIp.label)
            assertThat(it.response().items()[0].ip).isEqualTo(whitelistIp.ip)
        }
        verifyAll()
    }
}
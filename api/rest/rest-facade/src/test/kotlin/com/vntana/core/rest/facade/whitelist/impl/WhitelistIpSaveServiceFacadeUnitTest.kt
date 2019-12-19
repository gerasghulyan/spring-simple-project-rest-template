package com.vntana.core.rest.facade.whitelist.impl

import com.vntana.core.model.whitelist.error.WhitelistIpErrorResponseModel
import com.vntana.core.rest.facade.whitelist.AbstractWhitelistIpServiceFacadeUnitTest
import com.vntana.core.service.whitelist.mediator.dto.SaveWhitelistIpLifecycleDto
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 11/29/19
 * Time: 4:54 PM
 */
class WhitelistIpSaveServiceFacadeUnitTest : AbstractWhitelistIpServiceFacadeUnitTest() {

    @Test
    fun `test when organization not found`() {
        val organizationUuid = uuid()
        val request = testHelper.buildCreateOrUpdateWhitelistIpsRequest(organizationUuid = organizationUuid)
        resetAll()
        expect(organizationService.existsByUuid(organizationUuid)).andReturn(false)
        replayAll()
        assertBasicErrorResultResponse(whitelistIpServiceFacade.save(request), WhitelistIpErrorResponseModel.ORGANIZATION_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun `test when previous ips not found`() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val organizationUuid = organization.uuid
        val item = testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel()
        val createDto = commonTestHelper.buildCreateWhitelistIpDto(label = item.label, ip = item.ip, organizationUuid = organizationUuid)
        val whitelistIp = commonTestHelper.buildWhitelistIp(label = item.label, ip = item.ip, organization = organization)
        val request = testHelper.buildCreateOrUpdateWhitelistIpsRequest(organizationUuid = organizationUuid,
                whitelistIps = listOf(item))
        val dto = SaveWhitelistIpLifecycleDto(
                organization.uuid,
                organization.slug,
                request.whitelistIps.map { it.ip })
        resetAll()
        expect(organizationService.existsByUuid(organizationUuid)).andReturn(true)
        expect((whitelistIpService.getByOrganization(organizationUuid))).andReturn(listOf())
        expect(whitelistIpService.create(createDto)).andReturn(whitelistIp)
        expect(organizationService.getByUuid(organizationUuid)).andReturn(organization)
        expect(whitelistIpLifecycleMediator.onSaved(dto))
        replayAll()
        assertBasicSuccessResultResponse(whitelistIpServiceFacade.save(request))
        verifyAll()
    }

    @Test
    fun `test when previous ips not found and request contains duplicate ips`() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val organizationUuid = organization.uuid
        val ip = testHelper.validIp()
        val item1 = testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel(ip = ip)
        val item2 = testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel(ip = ip)
        val createDto = commonTestHelper.buildCreateWhitelistIpDto(label = item1.label, ip = item1.ip, organizationUuid = organizationUuid)
        val whitelistIp = commonTestHelper.buildWhitelistIp(label = item1.label, ip = item1.ip, organization = organization)
        val request = testHelper.buildCreateOrUpdateWhitelistIpsRequest(organizationUuid = organizationUuid,
                whitelistIps = listOf(item1, item2))
        val dto = SaveWhitelistIpLifecycleDto(
                organization.uuid,
                organization.slug,
                request.whitelistIps.map { it.ip })
        resetAll()
        expect(organizationService.existsByUuid(organizationUuid)).andReturn(true)
        expect((whitelistIpService.getByOrganization(organizationUuid))).andReturn(listOf())
        expect(whitelistIpService.create(createDto)).andReturn(whitelistIp)
        expect(organizationService.getByUuid(organizationUuid)).andReturn(organization)
        expect(whitelistIpLifecycleMediator.onSaved(dto))
        replayAll()
        assertBasicSuccessResultResponse(whitelistIpServiceFacade.save(request))
        verifyAll()
    }

    @Test
    fun `test when previous ips found`() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val organizationUuid = organization.uuid
        val item = testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel()
        val createDto = commonTestHelper.buildCreateWhitelistIpDto(label = item.label, ip = item.ip, organizationUuid = organizationUuid)
        val existingWhitelistIp1 = commonTestHelper.buildWhitelistIp(organization = organization)
        val existingWhitelistIp2 = commonTestHelper.buildWhitelistIp(organization = organization)
        val whitelistIp = commonTestHelper.buildWhitelistIp(label = item.label, ip = item.ip, organization = organization)
        val request = testHelper.buildCreateOrUpdateWhitelistIpsRequest(organizationUuid = organizationUuid,
                whitelistIps = listOf(item))
        val dto = SaveWhitelistIpLifecycleDto(
                organization.uuid,
                organization.slug,
                request.whitelistIps.map { it.ip })
        resetAll()
        expect(organizationService.existsByUuid(organizationUuid)).andReturn(true)
        expect((whitelistIpService.getByOrganization(organizationUuid))).andReturn(listOf(existingWhitelistIp1, existingWhitelistIp2))
        expect(whitelistIpService.delete(listOf(existingWhitelistIp1.uuid, existingWhitelistIp2.uuid))).andVoid()
        expect(whitelistIpService.create(createDto)).andReturn(whitelistIp)
        expect(organizationService.getByUuid(organizationUuid)).andReturn(organization)
        expect(whitelistIpLifecycleMediator.onSaved(dto))
        replayAll()
        assertBasicSuccessResultResponse(whitelistIpServiceFacade.save(request))
        verifyAll()
    }
}
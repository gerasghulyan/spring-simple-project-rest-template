package com.vntana.core.rest.resource.whitelist.test

import com.vntana.core.model.whitelist.request.WhitelistTypeModel
import com.vntana.core.rest.resource.whitelist.AbstractWhitelistIpWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 11/29/19
 * Time: 12:38 PM
 */
class WhitelistIpGetByOrganizationWebTest : AbstractWhitelistIpWebTest() {

    @Test
    fun `test when empty`() {
        whitelistIpResourceClient.getByOrganizationAndType(
            testHelper.buildGetWhitelistIpsRequest(
                uuid(),
                WhitelistTypeModel.API
            )
        ).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().totalCount()).isEqualTo(0)
            assertThat(it.response().items()).isEmpty()
        }
    }

    @Test
    fun `test when 1 whitelist ip exists`() {
        val organizationUuid = organizationTestHelper.persistOrganization().response().uuid
        val expectedType = WhitelistTypeModel.API
        val expectedWhitelistIp =
            testHelper.persistWhitelistIps(organizationUuid = organizationUuid, type = expectedType)
        testHelper.persistWhitelistIps(organizationUuid = organizationUuid, type = WhitelistTypeModel.EMBEDDED)
        val request = testHelper.buildGetWhitelistIpsRequest(organizationUuid, expectedType)
        whitelistIpResourceClient.getByOrganizationAndType(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().totalCount()).isEqualTo(1)
            assertThat(it.response().items()[0].organizationUuid).isEqualTo(organizationUuid)
            assertThat(it.response().items()[0].uuid).isEqualTo(expectedWhitelistIp)
        }
    }

    @Test
    fun `test when 2 whitelist ip exists for organization`() {
        organizationTestHelper.persistOrganization()
        val organizationUuid = organizationTestHelper.persistOrganization().response().uuid
        val label1 = uuid()
        val label2 = uuid()
        val ip1 = "192.168.1.1"
        val ip2 = "684D:1111:222:3333:4444:5555:6:77"
        val expectedType = WhitelistTypeModel.API
        testHelper.persistWhitelistIps(
            request = testHelper.buildCreateOrUpdateWhitelistIpsRequest(
                organizationUuid, listOf(
                    testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel(label1, ip1),
                    testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel(label2, ip2)
                ), expectedType
            )
        )
        testHelper.persistWhitelistIps(
            request = testHelper.buildCreateOrUpdateWhitelistIpsRequest(
                organizationUuid, listOf(
                    testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel(uuid(), ip1),
                    testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel(label2, uuid())
                ), WhitelistTypeModel.EMBEDDED
            )
        )
        val request = testHelper.buildGetWhitelistIpsRequest(organizationUuid, expectedType)
        whitelistIpResourceClient.getByOrganizationAndType(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().totalCount()).isEqualTo(2)
            assertThat(it.response().items()[0].organizationUuid).isEqualTo(organizationUuid)
            assertThat(it.response().items()[0].label).isEqualTo(label1)
            assertThat(it.response().items()[0].ip).isEqualTo(ip1)
            assertThat(it.response().items()[1].organizationUuid).isEqualTo(organizationUuid)
            assertThat(it.response().items()[1].label).isEqualTo(label2)
            assertThat(it.response().items()[1].ip).isEqualTo(ip2)
        }
    }
}
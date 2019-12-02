package com.vntana.core.rest.resource.whitelist.test

import com.vntana.core.model.whitelist.error.WhitelistIpErrorResponseModel
import com.vntana.core.rest.resource.whitelist.AbstractWhitelistIpWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 11/29/19
 * Time: 12:38 PM
 */
class WhitelistIpSaveWebTest : AbstractWhitelistIpWebTest() {

    @Test
    fun `test with invalid arguments`() {
        val request1 = testHelper.buildCreateOrUpdateWhitelistIpsRequest(organizationUuid = null)
        whitelistIpResourceClient.save(request1).let {
            assertBasicErrorResultResponse(it, WhitelistIpErrorResponseModel.MISSING_ORGANIZATION_UUID)
        }
        val request2 = testHelper.buildCreateOrUpdateWhitelistIpsRequest(whitelistIps = null)
        whitelistIpResourceClient.save(request2).let {
            assertBasicErrorResultResponse(it, WhitelistIpErrorResponseModel.MISSING_WHITELIST_IPS)
        }
    }

    @Test
    fun `test with invalid ip`() {
        val request = testHelper.buildCreateOrUpdateWhitelistIpsRequest(whitelistIps = listOf(
                testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel(ip = "256.200.0.0"),
                testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel())
        )
        whitelistIpResourceClient.save(request).let {
            assertBasicErrorResultResponse(it, WhitelistIpErrorResponseModel.INVALID_IP)
        }
    }

    @Test
    fun `test when organization not found`() {
        val request = testHelper.buildCreateOrUpdateWhitelistIpsRequest(organizationUuid = uuid())
        whitelistIpResourceClient.save(request).let {
            assertBasicErrorResultResponse(it, WhitelistIpErrorResponseModel.ORGANIZATION_NOT_FOUND)
        }
    }

    @Test
    fun `test duplicate ips`() {
        val organizationUuid1 = organizationTestHelper.persistOrganization().response().uuid
        val validIp = testHelper.validIp()
        val request = testHelper.buildCreateOrUpdateWhitelistIpsRequest(organizationUuid = organizationUuid1,
                whitelistIps = listOf(
                        testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel(ip = validIp),
                        testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel(ip = validIp)
                )
        )
        whitelistIpResourceClient.save(request).let {
            assertBasicSuccessResultResponse(it)
        }
        whitelistIpResourceClient.getByOrganization(organizationUuid1).let {
            assertThat(it.response().totalCount()).isEqualTo(1)
            assertThat(it.response().items()[0].organizationUuid).isEqualTo(organizationUuid1)
            assertThat(it.response().items()[0].ip).isEqualTo(validIp)
        }
    }

    @Test
    fun test() {
        val organizationUuid1 = organizationTestHelper.persistOrganization().response().uuid
        val organizationUuid2 = organizationTestHelper.persistOrganization().response().uuid
        testHelper.persistWhitelistIps(request = testHelper.buildCreateOrUpdateWhitelistIpsRequest(organizationUuid = organizationUuid1,
                whitelistIps = listOf(
                        testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel(),
                        testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel()
                )
        ))
        testHelper.persistWhitelistIps(request = testHelper.buildCreateOrUpdateWhitelistIpsRequest(organizationUuid = organizationUuid2,
                whitelistIps = listOf(
                        testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel(),
                        testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel()
                )
        ))
        val label = uuid()
        val ip = testHelper.validIp()
        val request = testHelper.buildCreateOrUpdateWhitelistIpsRequest(organizationUuid = organizationUuid1,
                whitelistIps = listOf(
                        testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel(label = label, ip = ip)
                ))
        whitelistIpResourceClient.save(request).let {
            assertBasicSuccessResultResponse(it)
        }
        whitelistIpResourceClient.getByOrganization(organizationUuid1).let {
            assertThat(it.response().totalCount()).isEqualTo(1)
            assertThat(it.response().items()[0].organizationUuid).isEqualTo(organizationUuid1)
            assertThat(it.response().items()[0].label).isEqualTo(label)
            assertThat(it.response().items()[0].ip).isEqualTo(ip)
        }
        whitelistIpResourceClient.getByOrganization(organizationUuid2).let {
            assertThat(it.response().totalCount()).isEqualTo(2)
            assertThat(it.response().items()[0].organizationUuid).isEqualTo(organizationUuid2)
            assertThat(it.response().items()[1].organizationUuid).isEqualTo(organizationUuid2)
        }
    }
}
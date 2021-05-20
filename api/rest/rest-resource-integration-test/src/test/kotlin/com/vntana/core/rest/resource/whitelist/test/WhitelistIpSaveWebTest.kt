package com.vntana.core.rest.resource.whitelist.test

import com.vntana.core.model.whitelist.error.WhitelistIpErrorResponseModel
import com.vntana.core.model.whitelist.request.WhitelistTypeModel
import com.vntana.core.rest.resource.whitelist.AbstractWhitelistIpWebTest
import org.apache.commons.lang3.StringUtils
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
        val request = testHelper.buildCreateOrUpdateWhitelistIpsRequest(
            whitelistIps = listOf(testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel(ip = null))
        )
        assertBasicErrorResultResponse(
            whitelistIpResourceClient.save(request),
            WhitelistIpErrorResponseModel.MISSING_IP
        )
        val request1 = testHelper.buildCreateOrUpdateWhitelistIpsRequest(
            whitelistIps = listOf(testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel(ip = StringUtils.EMPTY))
        )
        assertBasicErrorResultResponse(
            whitelistIpResourceClient.save(request1),
            WhitelistIpErrorResponseModel.MISSING_IP
        )
        val request2 = testHelper.buildCreateOrUpdateWhitelistIpsRequest(organizationUuid = null)
        assertBasicErrorResultResponse(
            whitelistIpResourceClient.save(request2),
            WhitelistIpErrorResponseModel.MISSING_ORGANIZATION_UUID
        )
        val request3 = testHelper.buildCreateOrUpdateWhitelistIpsRequest(whitelistIps = null)
        assertBasicErrorResultResponse(
            whitelistIpResourceClient.save(request3),
            WhitelistIpErrorResponseModel.MISSING_WHITELIST_IPS
        )
    }

    @Test
    fun `test with invalid ip`() {
        val request = testHelper.buildCreateOrUpdateWhitelistIpsRequest(
            whitelistIps = listOf(
                testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel(ip = "256.200.0.0"),
                testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel()
            )
        )
        assertBasicErrorResultResponse(
            whitelistIpResourceClient.save(request),
            WhitelistIpErrorResponseModel.INVALID_IP
        )
    }

    @Test
    fun `test when organization not found`() {
        val request = testHelper.buildCreateOrUpdateWhitelistIpsRequest(organizationUuid = uuid())
        assertBasicErrorResultResponse(
            whitelistIpResourceClient.save(request),
            WhitelistIpErrorResponseModel.ORGANIZATION_NOT_FOUND
        )
    }

    @Test
    fun `test duplicate ips`() {
        val organizationUuid1 = organizationTestHelper.persistOrganization().response().uuid
        val type = WhitelistTypeModel.EMBEDDED
        val validIp = testHelper.validIp()
        val request = testHelper.buildCreateOrUpdateWhitelistIpsRequest(
            organizationUuid = organizationUuid1,
            whitelistIps = listOf(
                testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel(ip = validIp),
                testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel(ip = validIp)
            ),
            type = type
        )
        assertBasicSuccessResultResponse(whitelistIpResourceClient.save(request))
        whitelistIpResourceClient.getByOrganizationAndType(
            testHelper.buildGetWhitelistIpsRequest(
                organizationUuid1,
                type
            )
        ).let {
            assertThat(it.response().totalCount()).isEqualTo(1)
            assertThat(it.response().items()[0].organizationUuid).isEqualTo(organizationUuid1)
            assertThat(it.response().items()[0].ip).isEqualTo(validIp)
        }
    }

    @Test
    fun test() {
        val organizationUuid1 = organizationTestHelper.persistOrganization().response().uuid
        val organizationUuid2 = organizationTestHelper.persistOrganization().response().uuid
        val type = WhitelistTypeModel.EMBEDDED
        testHelper.persistWhitelistIps(
            request = testHelper.buildCreateOrUpdateWhitelistIpsRequest(
                organizationUuid = organizationUuid1,
                whitelistIps = listOf(
                    testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel(),
                    testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel()
                ),
                type = type
            )
        )
        testHelper.persistWhitelistIps(
            request = testHelper.buildCreateOrUpdateWhitelistIpsRequest(
                organizationUuid = organizationUuid2,
                whitelistIps = listOf(
                    testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel(),
                    testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel()
                ),
                type = type
            )
        )
        val label = StringUtils.EMPTY
        val ip = testHelper.validIp()
        val request = testHelper.buildCreateOrUpdateWhitelistIpsRequest(
            organizationUuid = organizationUuid1,
            whitelistIps = listOf(
                testHelper.buildCreateOrUpdateWhitelistIpItemRequestModel(label = label, ip = ip)
            )
        )
        assertBasicSuccessResultResponse(whitelistIpResourceClient.save(request))
        whitelistIpResourceClient.getByOrganizationAndType(
            testHelper.buildGetWhitelistIpsRequest(
                organizationUuid1,
                type
            )
        ).let {
            assertThat(it.response().totalCount()).isEqualTo(1)
            assertThat(it.response().items()[0].organizationUuid).isEqualTo(organizationUuid1)
            assertThat(it.response().items()[0].label).isEqualTo(label)
            assertThat(it.response().items()[0].ip).isEqualTo(ip)
        }
        whitelistIpResourceClient.getByOrganizationAndType(
            testHelper.buildGetWhitelistIpsRequest(
                organizationUuid2,
                type
            )
        ).let {
            assertThat(it.response().totalCount()).isEqualTo(2)
            assertThat(it.response().items()[0].organizationUuid).isEqualTo(organizationUuid2)
            assertThat(it.response().items()[1].organizationUuid).isEqualTo(organizationUuid2)
        }
    }
}
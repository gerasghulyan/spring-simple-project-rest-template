package com.vntana.core.service.whitelist.impl

import com.vntana.core.domain.whitelist.WhitelistType
import com.vntana.core.service.whitelist.AbstractWhitelistIpServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 2:30 PM
 */
class WhitelistIpGetByOrganizationServiceIntegrationTest : AbstractWhitelistIpServiceIntegrationTest() {

    @Test
    fun `test when nothing exists`() {
        assertThat(whitelistIpService.getByOrganization(uuid())).isEmpty()
    }

    @Test
    fun test() {
        val organization = organizationTestHelper.persistOrganization()
        val whitelistIps = listOf(
            testHelper.persistWhitelistIp(organization = organization),
            testHelper.persistWhitelistIp(organization = organization, type = WhitelistType.API),
            testHelper.persistWhitelistIp(organization = organization, type = WhitelistType.EMBEDDED)
        )
        whitelistIpService.getByOrganization(organization.uuid).let {
            assertThat(it).containsAll(whitelistIps)
        }
    }
}
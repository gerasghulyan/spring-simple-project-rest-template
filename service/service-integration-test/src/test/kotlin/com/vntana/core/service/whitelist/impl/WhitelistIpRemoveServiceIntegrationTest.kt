package com.vntana.core.service.whitelist.impl

import com.vntana.core.service.whitelist.AbstractWhitelistIpServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 1:55 PM
 */
class WhitelistIpRemoveServiceIntegrationTest : AbstractWhitelistIpServiceIntegrationTest() {

    @Test
    fun `test when not found`() {
        whitelistIpService.delete(listOf(uuid()))
    }

    @Test
    fun test() {
        val organization = organizationTestHelper.persistOrganization()
        val whitelistIp1 = testHelper.persistWhitelistIp(organization = organization)
        val whitelistIp2 = testHelper.persistWhitelistIp(organization = organization)
        val whitelistIp3 = testHelper.persistWhitelistIp(organization = organization)
        whitelistIpService.delete(listOf(whitelistIp1.uuid, whitelistIp2.uuid))
        whitelistIpService.getByOrganization(organization.uuid).let {
            assertThat(it).containsOnly(whitelistIp3)
        }
    }
}
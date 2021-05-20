package com.vntana.core.service.whitelist.impl

import com.vntana.core.domain.whitelist.WhitelistType
import com.vntana.core.service.whitelist.AbstractWhitelistIpServiceIntegrationTest
import org.assertj.core.api.Assertions.*
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 5/20/21
 * Time: 11:14 AM
 */
class WhitelistIpGetByOrganizationAndTypeServiceIntegrationTest : AbstractWhitelistIpServiceIntegrationTest() {

    @Test
    fun `test when nothing exists`() {
        assertThat(whitelistIpService.getByOrganizationAndType(uuid(), WhitelistType.EMBEDDED)).isEmpty()
    }

    @Test
    fun test() {
        val type = WhitelistType.API
        val organization = organizationTestHelper.persistOrganization()
        val whitelistIp1 = testHelper.persistWhitelistIp(organization = organization, type = WhitelistType.API)
        val whitelistIp2 = testHelper.persistWhitelistIp(organization = organization, type = WhitelistType.API)
        val whitelistIp3 = testHelper.persistWhitelistIp(organization = organization, type = WhitelistType.EMBEDDED)

        whitelistIpService.getByOrganizationAndType(organization.uuid, type).let {
            assertThat(it).containsExactlyInAnyOrder(whitelistIp1, whitelistIp2)
            assertThat(it).doesNotContain(whitelistIp3)
        }
    }
}
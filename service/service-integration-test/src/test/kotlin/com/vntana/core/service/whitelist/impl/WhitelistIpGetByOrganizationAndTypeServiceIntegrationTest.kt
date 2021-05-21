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
        assertThat(whitelistIpService.getByOrganizationAndType(uuid(), WhitelistType.API)).isEmpty()
    }

    @Test
    fun test() {
        val type = WhitelistType.ADMIN
        val organization = organizationTestHelper.persistOrganization()
        val whitelistIp1 = testHelper.persistWhitelistIp(organization = organization, type = WhitelistType.ADMIN)
        val whitelistIp2 = testHelper.persistWhitelistIp(organization = organization, type = WhitelistType.ADMIN)
        val whitelistIp3 = testHelper.persistWhitelistIp(organization = organization, type = WhitelistType.API)

        whitelistIpService.getByOrganizationAndType(organization.uuid, type).let {
            assertThat(it).containsExactlyInAnyOrder(whitelistIp1, whitelistIp2)
            assertThat(it).doesNotContain(whitelistIp3)
        }
    }
}
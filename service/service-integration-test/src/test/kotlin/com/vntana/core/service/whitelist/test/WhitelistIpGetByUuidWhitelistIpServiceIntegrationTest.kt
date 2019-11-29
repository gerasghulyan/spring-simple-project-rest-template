package com.vntana.core.service.whitelist.test

import com.vntana.core.service.whitelist.AbstractWhitelistIpServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 1:55 PM
 */
class WhitelistIpGetByUuidWhitelistIpServiceIntegrationTest : AbstractWhitelistIpServiceIntegrationTest() {

    @Test
    fun `test when not found`() {
        assertThat(whitelistIpService.getByUuid(uuid())).isEmpty
    }

    @Test
    fun `test`() {
        val whitelistIp = testHelper.persistWhitelistIp()
        whitelistIpService.getByUuid(whitelistIp.uuid).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.get()).isEqualTo(whitelistIp)
        }
    }
}
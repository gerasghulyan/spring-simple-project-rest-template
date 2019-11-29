package com.vntana.core.service.whitelist.test

import com.vntana.core.service.whitelist.AbstractWhitelistIpServiceIntegrationTest
import com.vntana.core.service.whitelist.exception.WhitelistIpNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 1:55 PM
 */
class WhitelistIpUpdateServiceIntegrationTest : AbstractWhitelistIpServiceIntegrationTest() {

    @Test
    fun `test when not found`() {
        val updateDto = testHelper.buildUpdateWhitelistIpDto()
        assertThatThrownBy { (whitelistIpService.update(updateDto)) }
                .isExactlyInstanceOf(WhitelistIpNotFoundException::class.java)
    }

    @Test
    fun `test`() {
        val whitelistIp = testHelper.persistWhitelistIp()
        val updateDto = testHelper.buildUpdateWhitelistIpDto(uuid = whitelistIp.uuid)
        whitelistIpService.update(updateDto).run {
            assertThat(this.uuid).isEqualTo(whitelistIp.uuid)
            assertThat(this.label).isEqualTo(updateDto.label)
            assertThat(this.ip).isEqualTo(updateDto.ip)
            assertThat(this.organization).isEqualTo(whitelistIp.organization)
        }
    }
}
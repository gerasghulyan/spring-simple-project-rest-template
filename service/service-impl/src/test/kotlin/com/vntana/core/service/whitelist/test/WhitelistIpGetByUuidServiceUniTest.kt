package com.vntana.core.service.whitelist.test

import com.vntana.core.service.whitelist.AbstractWhitelistIpServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 3:48 PM
 */
class WhitelistIpGetByUuidServiceUniTest : AbstractWhitelistIpServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { whitelistIpService.findByUuid(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { whitelistIpService.findByUuid("") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        val uuid = uuid()
        resetAll()
        expect(whitelistIpRepository.findByUuid(uuid)).andReturn(Optional.empty())
        replayAll()
        whitelistIpService.findByUuid(uuid).let {
            assertThat(it).isEmpty
        }
        verifyAll()
    }

    @Test
    fun test() {
        val whitelistIp = testHelper.buildWhitelistIp(organization = organizationTestHelper.buildOrganization())
        val uuid = whitelistIp.uuid
        resetAll()
        expect(whitelistIpRepository.findByUuid(uuid)).andReturn(Optional.of(whitelistIp))
        replayAll()
        whitelistIpService.findByUuid(uuid).let {
            assertThat(it).isNotEmpty
            assertThat(it.get()).isEqualTo(whitelistIp)
        }
        verifyAll()
    }
}
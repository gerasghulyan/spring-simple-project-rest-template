package com.vntana.core.service.whitelist.test

import com.vntana.core.domain.whitelist.WhitelistIp
import com.vntana.core.service.whitelist.AbstractWhitelistIpServiceUnitTest
import com.vntana.core.service.whitelist.exception.WhitelistIpNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 3:48 PM
 */
class WhitelistIpUpdateServiceUniTest : AbstractWhitelistIpServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { whitelistIpService.update(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { testHelper.buildUpdateWhitelistIpDto(uuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { testHelper.buildUpdateWhitelistIpDto(uuid = "") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { testHelper.buildUpdateWhitelistIpDto(label = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { testHelper.buildUpdateWhitelistIpDto(label = "") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { testHelper.buildUpdateWhitelistIpDto(ip = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { testHelper.buildUpdateWhitelistIpDto(ip = "") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        val updateDto = testHelper.buildUpdateWhitelistIpDto()
        resetAll()
        expect(whitelistIpRepository.findByUuid(updateDto.uuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { whitelistIpService.update(updateDto) }
                .isExactlyInstanceOf(WhitelistIpNotFoundException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        val organization = organizationTestHelper.buildOrganization()
        val whitelistIp = testHelper.buildWhitelistIp(organization = organization)
        val updateDto = testHelper.buildUpdateWhitelistIpDto(uuid = whitelistIp.uuid)
        resetAll()
        expect(whitelistIpRepository.findByUuid(updateDto.uuid)).andReturn(Optional.of(whitelistIp))
        expect(whitelistIpRepository.save(isA(WhitelistIp::class.java))).andAnswer { getCurrentArguments()[0] as WhitelistIp }
        replayAll()
        whitelistIpService.update(updateDto).let {
            assertThat(it.uuid).isEqualTo(whitelistIp.uuid)
            assertThat(it.organization).isEqualTo(organization)
            assertThat(it.label).isEqualTo(updateDto.label)
            assertThat(it.ip).isEqualTo(updateDto.ip)
        }
        verifyAll()
    }
}
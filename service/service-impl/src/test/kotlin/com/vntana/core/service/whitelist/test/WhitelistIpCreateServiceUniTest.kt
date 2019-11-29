package com.vntana.core.service.whitelist.test

import com.vntana.core.domain.whitelist.WhitelistIp
import com.vntana.core.service.whitelist.AbstractWhitelistIpServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 3:48 PM
 */
class WhitelistIpCreateServiceUniTest : AbstractWhitelistIpServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        assertThatThrownBy { whitelistIpService.create(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { testHelper.buildCreateWhitelistIpDto(label = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { testHelper.buildCreateWhitelistIpDto(label = "") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { testHelper.buildCreateWhitelistIpDto(ip = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { testHelper.buildCreateWhitelistIpDto(ip = "") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { testHelper.buildCreateWhitelistIpDto(organizationUuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { testHelper.buildCreateWhitelistIpDto(organizationUuid = "") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun test() {
        val organization = organizationTestHelper.buildOrganization()
        val createDto = testHelper.buildCreateWhitelistIpDto(organizationUuid = organization.uuid)
        resetAll()
        expect(organizationService.getByUuid(createDto.organizationUuid)).andReturn(organization)
        expect(whitelistIpRepository.save(isA(WhitelistIp::class.java))).andAnswer { getCurrentArguments()[0] as WhitelistIp }
        replayAll()
        whitelistIpService.create(createDto).let {
            assertThat(it.label).isEqualTo(createDto.label)
            assertThat(it.ip).isEqualTo(createDto.ip)
            assertThat(it.organization.uuid).isEqualTo(createDto.organizationUuid)
        }
        verifyAll()
    }
}
package com.vntana.core.service.whitelist.test

import com.vntana.core.service.whitelist.AbstractWhitelistIpServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 3:48 PM
 */
class WhitelistIpFindByOrganizationServiceUniTest : AbstractWhitelistIpServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { whitelistIpService.getByOrganization(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { whitelistIpService.getByOrganization("") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        val organization = organizationTestHelper.buildOrganization()
        val organizationUuid = organization.uuid
        val whitelistIp = testHelper.buildWhitelistIp(organization = organization)
        resetAll()
        expect(whitelistIpRepository.findByOrganization_Uuid(organizationUuid)).andReturn(listOf(whitelistIp))
        replayAll()
        whitelistIpService.getByOrganization(organizationUuid).let {
            assertThat(it).containsOnly(whitelistIp)
        }
        verifyAll()
    }
}
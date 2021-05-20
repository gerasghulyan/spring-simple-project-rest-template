package com.vntana.core.service.whitelist.impl

import com.vntana.core.domain.whitelist.WhitelistType
import com.vntana.core.service.whitelist.AbstractWhitelistIpServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.eq
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 5/20/21
 * Time: 11:08 AM
 */
class WhitelistIpFindByOrganizationUuidAndTypeServiceUnitTest : AbstractWhitelistIpServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { whitelistIpService.getByOrganizationAndType(null, WhitelistType.EMBEDDED) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { whitelistIpService.getByOrganizationAndType("", WhitelistType.ADMIN) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { whitelistIpService.getByOrganizationAndType(uuid(), null) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        val organization = organizationTestHelper.buildOrganization()
        val organizationUuid = organization.uuid
        val type = WhitelistType.ADMIN
        val whitelistIp = testHelper.buildWhitelistIp(organization = organization, type = type)
        resetAll()
        expect(
            whitelistIpRepository.findByOrganization_UuidAndType(
                eq(organizationUuid),
                eq(type)
            )
        ).andReturn(listOf(whitelistIp))
        replayAll()
        whitelistIpService.getByOrganizationAndType(organizationUuid, type).let {
            assertThat(it).containsOnly(whitelistIp)
        }
        verifyAll()
    }
}
package com.vntana.core.service.whitelist.impl

import com.vntana.core.service.organization.exception.OrganizationNotFoundForUuidException
import com.vntana.core.service.whitelist.AbstractWhitelistIpServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 1:55 PM
 */
class WhitelistIpCreateServiceIntegrationTest : AbstractWhitelistIpServiceIntegrationTest() {

    @Test
    fun `test when organization not found`() {
        val createWhitelistIpDto = testHelper.buildCreateWhitelistIpDto()
        assertThatThrownBy { whitelistIpService.create(createWhitelistIpDto) }
                .isExactlyInstanceOf(OrganizationNotFoundForUuidException::class.java)
    }

    @Test
    fun test() {
        val organization = organizationTestHelper.persistOrganization()
        val createWhitelistIpDto = testHelper.buildCreateWhitelistIpDto(organizationUuid = organization.uuid)
        whitelistIpService.create(createWhitelistIpDto).let {
            assertThat(it.ip).isEqualTo(createWhitelistIpDto.ip)
            assertThat(it.label).isEqualTo(createWhitelistIpDto.label)
            assertThat(it.organization).isEqualTo(organization)
        }
    }
}
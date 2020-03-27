package com.vntana.core.service.token.impl

import com.vntana.core.service.token.AbstractTokenServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 10:44 AM
 */
class TokenInvitationOrganizationCreateServiceIntegrationTest : AbstractTokenServiceIntegrationTest() {

    @Test
    fun test() {
        val invitationOrganization = invitationOrganizationIntegrationTestHelper.persistInvitationOrganization()
        val invitationOrganizationUuid = invitationOrganization.uuid
        val dto = integrationTestHelper.buildCreateTokenInvitationOrganizationDto(
                invitationOrganizationUuid = invitationOrganizationUuid
        )
        tokenService.createTokenInvitationOrganization(dto).let {
            flushAndClear()
            assertThat(it.token).isEqualTo(dto.token)
            assertThat(it.invitationOrganization).isEqualTo(invitationOrganization)
        }
    }
}
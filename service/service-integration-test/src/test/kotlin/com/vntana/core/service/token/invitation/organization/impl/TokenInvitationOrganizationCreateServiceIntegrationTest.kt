package com.vntana.core.service.token.invitation.organization.impl

import com.vntana.core.service.token.invitation.organization.AbstractTokenInvitationOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Geras Ghulyan
 * Date: 11.04.20
 * Time: 23:37
 */
class TokenInvitationOrganizationCreateServiceIntegrationTest : AbstractTokenInvitationOrganizationServiceIntegrationTest() {

    @Test
    fun test() {
        val invitationOrganization = invitationOrganizationIntegrationTestHelper.persistInvitationOrganization()
        val invitationOrganizationUuid = invitationOrganization.uuid
        val dto = integrationTestHelper.buildCreateTokenInvitationOrganizationDto(
                invitationOrganizationUuid = invitationOrganizationUuid
        )
        tokenInvitationOrganizationService.create(dto).let {
            flushAndClear()
            assertThat(it.token).isEqualTo(dto.token)
            assertThat(it.invitationOrganization).isEqualTo(invitationOrganization)
        }
    }
}
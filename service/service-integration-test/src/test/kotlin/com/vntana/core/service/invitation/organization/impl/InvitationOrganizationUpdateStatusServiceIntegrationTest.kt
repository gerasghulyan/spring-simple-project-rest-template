package com.vntana.core.service.invitation.organization.impl

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.service.invitation.organization.AbstractInvitationOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 4/9/2020
 * Time: 10:53 AM
 */
class InvitationOrganizationUpdateStatusServiceIntegrationTest : AbstractInvitationOrganizationServiceIntegrationTest() {

    @Test
    fun `test update`() {
        val invitationOrganization = integrationTestHelper.persistInvitationOrganization()
        val dto = integrationTestHelper.buildUpdateInvitationOrganizationStatusDto(
                uuid = invitationOrganization.uuid,
                status = InvitationStatus.REJECTED
        )
        invitationOrganizationService.updateStatus(dto).let { 
            assertThat(it.status).isEqualTo(dto.status)
        }
        flushAndClear()
    }
}
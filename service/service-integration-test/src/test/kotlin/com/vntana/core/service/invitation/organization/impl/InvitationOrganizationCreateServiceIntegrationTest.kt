package com.vntana.core.service.invitation.organization.impl

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.service.invitation.organization.AbstractInvitationOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/25/20
 * Time: 4:04 PM
 */
class InvitationOrganizationCreateServiceIntegrationTest : AbstractInvitationOrganizationServiceIntegrationTest() {

    @Test
    fun test() {
        val dto = integrationTestHelper.buildCreateInvitationOrganizationDto()
        invitationOrganizationService.create(dto).let {
            flushAndClear()
            assertThat(it.customerSubscriptionDefinitionUuid).isEqualTo(dto.customerSubscriptionDefinitionUuid)
            assertThat(it.email).isEqualTo(dto.email)
            assertThat(it.organizationName).isEqualTo(dto.organizationName)
            assertThat(it.ownerFullName).isEqualTo(dto.ownerFullName)
            assertThat(it.slug).isEqualTo(dto.slug)
            assertThat(it.status).isEqualTo(InvitationStatus.INVITED)
        }
    }
}
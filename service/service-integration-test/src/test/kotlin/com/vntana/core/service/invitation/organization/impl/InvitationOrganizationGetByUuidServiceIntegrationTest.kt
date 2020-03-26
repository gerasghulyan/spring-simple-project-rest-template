package com.vntana.core.service.invitation.organization.impl

import com.vntana.core.service.invitation.organization.AbstractInvitationOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/25/20
 * Time: 4:05 PM
 */
class InvitationOrganizationGetByUuidServiceIntegrationTest : AbstractInvitationOrganizationServiceIntegrationTest() {

    @Test
    fun test() {
        val invitationOrganization = integrationTestHelper.persistInvitationOrganization()
        flushAndClear()
        assertThat(invitationOrganizationService.getByUuid(invitationOrganization.uuid)).isEqualTo(invitationOrganization)
    }
}
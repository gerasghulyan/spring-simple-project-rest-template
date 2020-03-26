package com.vntana.core.service.invitation.organization.impl

import com.vntana.core.service.invitation.organization.AbstractInvitationOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/25/20
 * Time: 4:05 PM
 */
class InvitationOrganizationExistsByUuidServiceIntegrationTest : AbstractInvitationOrganizationServiceIntegrationTest() {

    @Test
    fun `test when not found`() {
        assertThat(invitationOrganizationService.existsByUuid(uuid())).isFalse()
    }

    @Test
    fun `test when found`() {
        val uuid = integrationTestHelper.persistInvitationOrganization().uuid
        flushAndClear()
        assertThat(invitationOrganizationService.existsByUuid(uuid)).isTrue()
    }
}
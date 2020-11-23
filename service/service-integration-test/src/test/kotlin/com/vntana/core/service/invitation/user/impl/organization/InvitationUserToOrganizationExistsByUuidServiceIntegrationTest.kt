package com.vntana.core.service.invitation.user.impl.organization

import com.vntana.core.service.invitation.user.AbstractInvitationUserToOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:43 PM
 */
class InvitationUserToOrganizationExistsByUuidServiceIntegrationTest : AbstractInvitationUserToOrganizationServiceIntegrationTest() {

    @Test
    fun `test when not found`() {
        assertThat(invitationUserToOrganizationService.existsByUuid(uuid())).isFalse()
    }

    @Test
    fun `test when found`() {
        val uuid = integrationInvitationUserTestHelper.persistInvitationUserToOrganization(
                inviterUserUuid = userIntegrationTestHelper.persistUserWithOwnerRole().uuid,
                organizationUuid = organizationIntegrationTestHelper.persistOrganization().uuid
        ).uuid
        flushAndClear()
        assertThat(invitationUserToOrganizationService.existsByUuid(uuid)).isTrue()
    }
}
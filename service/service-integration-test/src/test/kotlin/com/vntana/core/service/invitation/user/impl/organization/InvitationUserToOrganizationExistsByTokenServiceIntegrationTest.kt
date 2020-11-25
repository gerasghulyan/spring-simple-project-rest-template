package com.vntana.core.service.invitation.user.impl.organization

import com.vntana.core.service.invitation.user.AbstractInvitationUserToOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 8:36 PM
 */
class InvitationUserToOrganizationExistsByTokenServiceIntegrationTest : AbstractInvitationUserToOrganizationServiceIntegrationTest() {

    @Test
    fun `test not found`() {
        assertThat(invitationUserToOrganizationService.existsByToken(uuid())).isFalse()
    }

    @Test
    fun test() {
        val invitationUser = integrationInvitationUserTestHelper.persistInvitationUserToOrganization()
        val token = tokenInvitationUserIntegrationTestHelper.persistTokenInvitationUserToOrganization(invitationUserUuid = invitationUser.uuid).token
        flushAndClear()
        assertThat(invitationUserToOrganizationService.existsByToken(token)).isTrue()
    }
}
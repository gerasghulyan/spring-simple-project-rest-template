package com.vntana.core.service.invitation.user.impl.organization

import com.vntana.core.service.invitation.user.AbstractInvitationUserToOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:43 PM
 */
class InvitationUserToOrganizationGetByUuidServiceIntegrationTest : AbstractInvitationUserToOrganizationServiceIntegrationTest() {
    @Test
    fun test() {
        val invitationUser = integrationInvitationUserTestHelper.persistInvitationUserToOrganization(
                inviterUserUuid = userIntegrationTestHelper.persistUserWithOwnerRole().uuid
        )
        flushAndClear()
        assertThat(invitationUserToOrganizationService.getByUuid(invitationUser.uuid)).isEqualTo(invitationUser)
    }
}
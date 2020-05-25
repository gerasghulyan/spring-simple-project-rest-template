package com.vntana.core.service.invitation.user.impl

import com.vntana.core.service.invitation.user.AbstractInvitationUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:43 PM
 */
class InvitationUserExistsByUuidServiceIntegrationTest : AbstractInvitationUserServiceIntegrationTest() {

    @Test
    fun `test when not found`() {
        assertThat(invitationUserService.existsByUuid(uuid())).isFalse()
    }

    @Test
    fun `test when found`() {
        val uuid = integrationInvitationUserTestHelper.persistInvitationUser(
                inviterUserUuid = userIntegrationTestHelper.persistUserWithOwnerRole().uuid,
                organizationUuid = organizationIntegrationTestHelper.persistOrganization().uuid
        ).uuid
        flushAndClear()
        assertThat(invitationUserService.existsByUuid(uuid)).isTrue()
    }
}
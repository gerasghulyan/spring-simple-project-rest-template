package com.vntana.core.service.invitation.user.impl.organization

import com.vntana.core.service.invitation.user.AbstractInvitationUserToOrganizationServiceIntegrationTest
import com.vntana.core.service.invitation.user.exception.InvitationUserNotFoundForTokenException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 8:23 PM
 */
class InvitationUserToOrganizationGetByTokenServiceIntegrationTest : AbstractInvitationUserToOrganizationServiceIntegrationTest() {

    @Test
    fun `test not found`() {
        assertThatThrownBy { invitationUserToOrganizationService.getByToken(uuid()) }
                .isExactlyInstanceOf(InvitationUserNotFoundForTokenException::class.java)
    }

    @Test
    fun test() {
        val invitationUser = integrationInvitationUserTestHelper.persistInvitationUserToOrganization()
        val token = tokenInvitationUserIntegrationTestHelper.persistTokenInvitationUserToOrganization(invitationUserUuid = invitationUser.uuid).token
        flushAndClear()
        invitationUserToOrganizationService.getByToken(token).let {
            assertThat(it).isNotNull
            assertThat(it).isEqualTo(invitationUser)
        }
    }
}
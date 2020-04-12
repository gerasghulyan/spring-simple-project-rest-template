package com.vntana.core.service.token.invitation.organization.impl

import com.vntana.core.service.token.invitation.organization.AbstractTokenInvitationOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Geras Ghulyan
 * Date: 4/9/2020
 * Time: 2:31 PM
 */
class TokenInvitationOrganizationExistsByTokenServiceIntegrationTest : AbstractTokenInvitationOrganizationServiceIntegrationTest() {

    @Test
    fun `test when not found`() {
        assertThat(tokenInvitationOrganizationService.existsByToken(uuid())).isFalse()
    }

    @Test
    fun `test existsByToken`() {
        val token = uuid()
        invitationOrganizationIntegrationTestHelper.persistInvitationOrganization(token = token)
        flushAndClear()
        assertThat(tokenInvitationOrganizationService.existsByToken(token)).isTrue()
    }
}
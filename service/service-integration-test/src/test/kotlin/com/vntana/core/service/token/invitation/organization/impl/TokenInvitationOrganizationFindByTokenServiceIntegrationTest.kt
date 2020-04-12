package com.vntana.core.service.token.invitation.organization.impl

import com.vntana.core.service.token.invitation.organization.AbstractTokenInvitationOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Geras Ghulyan
 * Date: 4/9/2020
 * Time: 2:31 PM
 */
class TokenInvitationOrganizationFindByTokenServiceIntegrationTest : AbstractTokenInvitationOrganizationServiceIntegrationTest() {

    @Test
    fun `test findByToken not found`() {
        assertThat(tokenInvitationOrganizationService.findByToken(uuid())).isNotPresent
    }

    @Test
    fun `test findByToken`() {
        val token = uuid()
        val persistInvitationOrganization = invitationOrganizationIntegrationTestHelper.persistInvitationOrganization(token = token)
        flushAndClear()
        tokenInvitationOrganizationService.findByToken(token).let {
            assertThat(it).isPresent
            assertThat(it.get()).hasFieldOrPropertyWithValue("token", token)
        }
    }
}
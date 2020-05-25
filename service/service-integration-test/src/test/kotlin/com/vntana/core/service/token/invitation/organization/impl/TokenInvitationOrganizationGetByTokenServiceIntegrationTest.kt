package com.vntana.core.service.token.invitation.organization.impl

import com.vntana.core.service.token.exception.TokenNotFoundException
import com.vntana.core.service.token.invitation.organization.AbstractTokenInvitationOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Geras Ghulyan.
 * Date: 4/9/2020
 * Time: 2:31 PM
 */
class TokenInvitationOrganizationGetByTokenServiceIntegrationTest : AbstractTokenInvitationOrganizationServiceIntegrationTest() {

    @Test
    fun `test getByToken not found`() {
        assertThatThrownBy { tokenInvitationOrganizationService.getByToken(uuid()) }.isExactlyInstanceOf(TokenNotFoundException::class.java)
    }

    @Test
    fun `test getByToken`() {
        val token = uuid()
        val persistInvitationOrganization = invitationOrganizationIntegrationTestHelper.persistInvitationOrganization()
        integrationTestHelper.persistTokenInvitationOrganization(token, persistInvitationOrganization.uuid)
        flushAndClear()
        assertThat(tokenInvitationOrganizationService.getByToken(token)).hasFieldOrPropertyWithValue("token", token)
    }
}
package com.vntana.core.service.token.impl

import com.vntana.core.service.token.AbstractTokenServiceIntegrationTest
import com.vntana.core.service.token.exception.TokenNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 4/9/2020
 * Time: 2:31 PM
 */
class TokenGetByTokenServiceIntegrationTest : AbstractTokenServiceIntegrationTest() {

    @Test
    fun `test getByToken not found`() {
        assertThatThrownBy { tokenService.getByToken(uuid()) }.isExactlyInstanceOf(TokenNotFoundException::class.java)
    }

    @Test
    fun `test getByToken`() {
        val token = uuid()
        val persistInvitationOrganization = invitationOrganizationIntegrationTestHelper.persistInvitationOrganization()
        integrationTestHelper.persistTokenInvitationOrganization(token, persistInvitationOrganization.uuid)
        flushAndClear()
        assertThat(tokenService.getByToken(token)).hasFieldOrPropertyWithValue("token", token)
    }
}
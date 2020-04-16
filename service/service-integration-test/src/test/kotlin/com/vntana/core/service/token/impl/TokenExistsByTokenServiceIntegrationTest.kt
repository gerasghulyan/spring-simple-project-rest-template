package com.vntana.core.service.token.impl

import com.vntana.core.service.token.AbstractTokenServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 4/9/2020
 * Time: 2:31 PM
 */
class TokenExistsByTokenServiceIntegrationTest : AbstractTokenServiceIntegrationTest() {

    @Test
    fun `test existsByToken not found`() {
        assertThat(tokenService.existsByToken(uuid())).isFalse()
    }

    @Test
    fun `test existsByToken`() {
        val token = uuid()
        val persistInvitationOrganization = invitationOrganizationIntegrationTestHelper.persistInvitationOrganization()
        flushAndClear()
        integrationTestHelper.persistTokenInvitationOrganization(token, persistInvitationOrganization.uuid)
        assertThat(tokenService.existsByToken(token)).isTrue()
    }
}
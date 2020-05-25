package com.vntana.core.service.token.impl

import com.vntana.core.service.token.AbstractTokenServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 4/9/2020
 * Time: 3:30 PM
 */
class TokenFindByTokenAndExpireServiceIntegrationTest : AbstractTokenServiceIntegrationTest() {

    @Test
    fun `test findByTokenAndExpire`() {
        val token = uuid()
        val persistInvitationOrganization = invitationOrganizationIntegrationTestHelper.persistInvitationOrganization()
        integrationTestHelper.persistTokenInvitationOrganization(token, persistInvitationOrganization.uuid)
        flushAndClear()
        tokenService.findByTokenAndExpire(token).let {
            assertThat(it).isNotNull
            assertThat(it.isExpired).isTrue()
        }
    }
}
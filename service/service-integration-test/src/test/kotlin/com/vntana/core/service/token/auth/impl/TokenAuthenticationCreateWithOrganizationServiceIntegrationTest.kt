package com.vntana.core.service.token.auth.impl

import com.vntana.core.service.token.auth.AbstractTokenAuthenticationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 3:18 PM
 */
class TokenAuthenticationCreateWithOrganizationServiceIntegrationTest : AbstractTokenAuthenticationServiceIntegrationTest() {

    @Test
    fun test() {
        val user = userIntegrationTestHelper.persistUserWithOwnerRole()
        val organization = organizationIntegrationTestHelper.persistOrganization()
        val dto = integrationTestHelper.buildTokenAuthenticationCreateWithOrganizationDto(userUuid = user.uuid, organizationUuid = organization.uuid)
        val token = tokenAuthenticationService.createWithOrganization(dto)
        flushAndClear()
        assertThat(token.isExpired).isFalse()
        assertThat(token.user).isEqualTo(user)
        assertThat(token.token).isEqualTo(dto.token)
        assertThat(token.organization.uuid).isEqualTo(dto.organizationUuid)
    }
}
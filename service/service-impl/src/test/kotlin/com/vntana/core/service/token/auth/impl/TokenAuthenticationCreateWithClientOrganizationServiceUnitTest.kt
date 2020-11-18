package com.vntana.core.service.token.auth.impl

import com.vntana.core.domain.token.TokenAuthentication
import com.vntana.core.service.token.auth.AbstractTokenAuthenticationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 3:33 PM
 */
class TokenAuthenticationCreateWithClientOrganizationServiceUnitTest : AbstractTokenAuthenticationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildTokenAuthenticationCreateWithClientOrganizationDto(userUuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildTokenAuthenticationCreateWithClientOrganizationDto(userUuid = "") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildTokenAuthenticationCreateWithClientOrganizationDto(token = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildTokenAuthenticationCreateWithClientOrganizationDto(token = "") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildTokenAuthenticationCreateWithClientOrganizationDto(clientUuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildTokenAuthenticationCreateWithClientOrganizationDto(clientUuid = "") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenAuthenticationService.createWithClientOrganization(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val user = userCommonTestHelper.buildUserWithOrganizationOwnerRole()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val dto = commonTestHelper.buildTokenAuthenticationCreateWithClientOrganizationDto(userUuid = user.uuid, clientUuid = clientOrganization.uuid)
        expect(userService.getByUuid(dto.userUuid)).andReturn(user)
        expect(organizationClientService.getByUuid(dto.clientUuid)).andReturn(clientOrganization)
        expect(tokenAuthenticationRepository.save(isA(TokenAuthentication::class.java))).andAnswer { getCurrentArguments()[0] as TokenAuthentication }
        replayAll()
        tokenAuthenticationService.createWithClientOrganization(dto).let {
            assertThat(it.token).isEqualTo(dto.token)
            assertThat(it.user.uuid).isEqualTo(dto.userUuid)
            assertThat(it.clientOrganization.uuid).isEqualTo(dto.clientUuid)
            assertThat(it.isExpired).isEqualTo(false)
        }
        verifyAll()
    }
}
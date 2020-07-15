package com.vntana.core.service.token.auth.impl

import com.vntana.core.domain.token.AuthToken
import com.vntana.core.service.token.auth.AbstractAuthTokenServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 3:33 PM
 */
class AuthTokenCreateWithOrganizationServiceUnitTest : AbstractAuthTokenServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildAuthTokenCreateWithOrganizationDto(userUuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAuthTokenCreateWithOrganizationDto(userUuid = "") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAuthTokenCreateWithOrganizationDto(token = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAuthTokenCreateWithOrganizationDto(token = "") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAuthTokenCreateWithOrganizationDto(organizationUuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAuthTokenCreateWithOrganizationDto(organizationUuid = "") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { authTokenService.createWithOrganization(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val user = userCommonTestHelper.buildUserWithOrganizationOwnerRole()
        val organization = organizationCommonTestHelper.buildOrganization()
        val dto = commonTestHelper.buildAuthTokenCreateWithOrganizationDto(userUuid = user.uuid)
        expect(userService.getByUuid(dto.userUuid)).andReturn(user)
        expect(organizationService.getByUuid(dto.organizationUuid)).andReturn(organization)
        expect(authTokenRepository.save(isA(AuthToken::class.java))).andAnswer { getCurrentArguments()[0] as AuthToken }
        replayAll()
        authTokenService.createWithOrganization(dto).let {
            assertThat(it.token).isEqualTo(dto.token)
            assertThat(it.user.uuid).isEqualTo(dto.userUuid)
            assertThat(it.isExpired).isEqualTo(false)
        }
        verifyAll()
    }
}
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
class AuthTokenCreateServiceUnitTest : AbstractAuthTokenServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildAuthTokenCreateDto(userUuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAuthTokenCreateDto(userUuid = "") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAuthTokenCreateDto(token = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAuthTokenCreateDto(token = "") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { authTokenService.create(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val user = userCommonTestHelper.buildUserWithOrganizationOwnerRole()
        val dto = commonTestHelper.buildAuthTokenCreateDto(userUuid = user.uuid)
        expect(userService.getByUuid(dto.userUuid)).andReturn(user)
        expect(authTokenRepository.save(isA(AuthToken::class.java))).andAnswer { getCurrentArguments()[0] as AuthToken }
        replayAll()
        authTokenService.create(dto).let {
            assertThat(it.token).isEqualTo(dto.token)
            assertThat(it.user.uuid).isEqualTo(dto.userUuid)
            assertThat(it.isExpired).isEqualTo(false)
        }
        verifyAll()
    }
}
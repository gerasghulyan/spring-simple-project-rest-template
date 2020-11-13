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
class TokenAuthenticationCreateServiceUnitTest : AbstractTokenAuthenticationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildTokenAuthenticationCreateDto(userUuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildTokenAuthenticationCreateDto(userUuid = "") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildTokenAuthenticationCreateDto(token = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildTokenAuthenticationCreateDto(token = "") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenAuthenticationService.create(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val user = userCommonTestHelper.buildUserWithOrganizationOwnerRole()
        val dto = commonTestHelper.buildTokenAuthenticationCreateDto(userUuid = user.uuid)
        expect(userService.getByUuid(dto.userUuid)).andReturn(user)
        expect(tokenAuthenticationRepository.save(isA(TokenAuthentication::class.java))).andAnswer { getCurrentArguments()[0] as TokenAuthentication }
        replayAll()
        tokenAuthenticationService.create(dto).let {
            assertThat(it.token).isEqualTo(dto.token)
            assertThat(it.user.uuid).isEqualTo(dto.userUuid)
            assertThat(it.isExpired).isEqualTo(false)
        }
        verifyAll()
    }
}
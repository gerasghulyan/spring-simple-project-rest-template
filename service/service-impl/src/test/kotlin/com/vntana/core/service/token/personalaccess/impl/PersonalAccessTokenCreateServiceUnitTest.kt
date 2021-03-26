package com.vntana.core.service.token.personalaccess.impl

import com.vntana.core.domain.token.TokenPersonalAccess
import com.vntana.core.service.token.personalaccess.AbstractPersonalAccessTokenServiceUnitTest
import com.vntana.core.service.token.personalaccess.dto.CreatePersonalAccessTokenDto
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 12:33 AM
 */
class PersonalAccessTokenCreateServiceUnitTest : AbstractPersonalAccessTokenServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { personalAccessTokenService.create(null) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val token = uuid()
        val user = userCommonTestHelper.buildUser()
        val dto = CreatePersonalAccessTokenDto(user.uuid, token)
        val personalAccessToken = commonTestHelper.buildTokenPersonalAccess(token, user)
        expect(userService.getByUuid(eq(dto.userUuid))).andReturn(user)
        expect(tokenPersonalAccessRepository.save(anyObject(TokenPersonalAccess::class.java))).andReturn(personalAccessToken)
        replayAll()
        personalAccessTokenService.create(dto).let {
            assertThat(it.user).isEqualTo(user)
            assertThat(it.token).isEqualTo(token)
        }
    }
}
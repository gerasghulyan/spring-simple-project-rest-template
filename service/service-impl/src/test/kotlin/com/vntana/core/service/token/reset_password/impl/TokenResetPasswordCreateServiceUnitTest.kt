package com.vntana.core.service.token.reset_password.impl

import com.vntana.core.domain.token.TokenResetPassword
import com.vntana.core.service.token.reset_password.AbstractTokenResetPasswordServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test

/**
 * Created by Geras Ghulyan
 * Date: 6/23/20
 * Time: 1:12 PM
 */
class TokenResetPasswordCreateServiceUnitTest : AbstractTokenResetPasswordServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { tokenResetPasswordService.create(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenResetPasswordService.create(commonTestHelper.buildCreateTokenResetPasswordDto(token = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenResetPasswordService.create(commonTestHelper.buildCreateTokenResetPasswordDto(userUuid = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val user = userCommonTestHelper.buildUser()
        val dto = commonTestHelper.buildCreateTokenResetPasswordDto(
                userUuid = user.uuid
        )
        expect(userService.getByUuid(dto.userUuid)).andReturn(user)
        expect(tokenResetPasswordRepository.save(isA(TokenResetPassword::class.java))).andAnswer { getCurrentArguments()[0] as TokenResetPassword }
        replayAll()
        tokenResetPasswordService.create(dto).let {
            assertThat(it.token).isEqualTo(dto.token)
            assertThat(it.user.uuid).isEqualTo(dto.userUuid)
        }
        verifyAll()
    }

}
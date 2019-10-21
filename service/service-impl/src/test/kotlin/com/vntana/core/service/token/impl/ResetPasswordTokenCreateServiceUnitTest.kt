package com.vntana.core.service.token.impl

import com.vntana.core.domain.token.ResetPasswordToken
import com.vntana.core.service.token.AbstractTokenServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 2:38 PM
 */
class ResetPasswordTokenCreateServiceUnitTest : AbstractTokenServiceUnitTest() {
    @Test
    fun `test with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { resetPasswordTokenService.create(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { resetPasswordTokenService.create(helper.buildCreateResetPasswordTokenDto(userUuid = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test create when user not found`() {
        // test data
        resetAll()
        val dto = helper.buildCreateResetPasswordTokenDto()
        // expectations
        expect(userService.findByUuid(dto.userUuid)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        assertThatThrownBy { resetPasswordTokenService.create(dto) }
                .isExactlyInstanceOf(IllegalStateException::class.java)
        verifyAll()
    }

    @Test
    fun `test create`() {
        // test data
        resetAll()
        val dto = helper.buildCreateResetPasswordTokenDto()
        val user = userHelper.buildUser()
        // expectations
        expect(userService.findByUuid(dto.userUuid)).andReturn(Optional.of(user))
        expect(resetPasswordTokenRepository.save(isA(ResetPasswordToken::class.java)))
                .andAnswer { getCurrentArguments()[0] as ResetPasswordToken }
        replayAll()
        // test scenario
        val result = resetPasswordTokenService.create(dto)
        assertThat(result).hasFieldOrPropertyWithValue("user", user)
        assertThat(result.token).isNotBlank()
        verifyAll()
    }
}
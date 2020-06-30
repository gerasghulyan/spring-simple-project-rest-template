package com.vntana.core.rest.facade.user.impl

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.user.AbstractUserServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan.
 * Date: 12/12/19
 * Time: 6:59 PM
 */
class UserCheckResetPasswordServiceFacadeUnitTest : AbstractUserServiceFacadeUnitTest() {

    @Test
    fun `test when token not found`() {
        val request = restHelper.buildResetUserPasswordRequest()
        resetAll()
        expect(tokenService.findByToken(request.token)).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(userServiceFacade.checkResetPasswordToken(request.token), UserErrorResponseModel.INVALID_RESET_PASSWORD_TOKEN)
        verifyAll()
    }

    @Test
    fun `test when token is not type of reset password token`() {
        val request = restHelper.buildResetUserPasswordRequest()
        val abstractToken = tokenCommonTestHelper.buildTokenInvitationOrganization()
        resetAll()
        expect(tokenService.findByToken(request.token)).andReturn(Optional.of(abstractToken))
        replayAll()
        assertBasicErrorResultResponse(userServiceFacade.checkResetPasswordToken(request.token), UserErrorResponseModel.INVALID_RESET_PASSWORD_TOKEN)
        verifyAll()
    }

    @Test
    fun test() {
        val newPassword = uuid()
        val token = uuid()
        val user = userHelper.buildUserWithOrganizationOwnerRole(password = newPassword)
        val request = restHelper.buildResetUserPasswordRequest(token = token, password = newPassword)
        val resetPasswordToken = tokenCommonTestHelper.buildTokenResetPassword(token = request.token)
        resetAll()
        expect(tokenService.findByToken(request.token)).andReturn(Optional.of(resetPasswordToken))
        replayAll()
        userServiceFacade.checkResetPasswordToken(request.token).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.success()).isTrue()
        }
        verifyAll()
    }
}
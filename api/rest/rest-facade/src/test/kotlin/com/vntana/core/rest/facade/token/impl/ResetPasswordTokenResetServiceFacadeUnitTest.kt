package com.vntana.core.rest.facade.token.impl

import com.vntana.core.model.token.error.TokenErrorResponseModel
import com.vntana.core.rest.facade.token.AbstractTokenServiceFacadeUnitTest
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 3:31 PM
 */
class ResetPasswordTokenResetServiceFacadeUnitTest : AbstractTokenServiceFacadeUnitTest() {
    @Test
    fun `test reset when user not found`() {
        // test data
        resetAll()
        val request = restTestHelper.buildResetPasswordRequest()
        // expectations
        expect(userService.findByEmail(request.email)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        restTestHelper.assertBasicErrorResultResponse(resetPasswordTokenServiceFacade.reset(request), TokenErrorResponseModel.USER_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun `test reset`() {
        // test data
        resetAll()
        val request = restTestHelper.buildResetPasswordRequest()
        val user = userUnitTestHelper.buildUser()
        val resetPasswordToken = unitTestHelper.buildResetPasswordToken()
        // expectations
        expect(userService.findByEmail(request.email)).andReturn(Optional.of(user))
        expect(resetPasswordTokenService.create(unitTestHelper.buildCreateResetPasswordTokenDto(userUuid = user.uuid))).andReturn(resetPasswordToken)
        replayAll()
        // test scenario
        restTestHelper.assertBasicSuccessResultResponse(resetPasswordTokenServiceFacade.reset(request))
        verifyAll()
    }
}
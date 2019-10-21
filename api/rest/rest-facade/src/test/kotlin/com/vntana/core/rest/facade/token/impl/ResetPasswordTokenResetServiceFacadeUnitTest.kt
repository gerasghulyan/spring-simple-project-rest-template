package com.vntana.core.rest.facade.token.impl

import com.vntana.core.model.token.error.TokenErrorResponseModel
import com.vntana.core.rest.facade.token.AbstractTokenServiceFacadeUnitTest
import com.vntana.core.rest.facade.token.ResetPasswordTokenServiceFacade
import com.vntana.core.rest.facade.token.component.CreateResetPasswordTokenMediator
import org.easymock.EasyMock.expect
import org.easymock.Mock
import org.junit.Before
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 3:31 PM
 */
class ResetPasswordTokenResetServiceFacadeUnitTest : AbstractTokenServiceFacadeUnitTest() {

    private lateinit var resetPasswordTokenServiceFacade: ResetPasswordTokenServiceFacade

    @Mock
    private lateinit var createResetPasswordTokenMediator: CreateResetPasswordTokenMediator

    @Before
    fun beforeThis() {
        resetPasswordTokenServiceFacade = ResetPasswordTokenServiceFacadeImpl(userService, createResetPasswordTokenMediator)
    }

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
        // expectations
        expect(userService.findByEmail(request.email)).andReturn(Optional.of(user))
        createResetPasswordTokenMediator.create(user.uuid)
        replayAll()
        // test scenario
        restTestHelper.assertBasicSuccessResultResponse(resetPasswordTokenServiceFacade.reset(request))
        verifyAll()
    }
}
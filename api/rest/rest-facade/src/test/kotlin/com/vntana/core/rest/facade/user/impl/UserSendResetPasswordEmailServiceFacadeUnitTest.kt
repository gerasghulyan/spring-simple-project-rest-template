package com.vntana.core.rest.facade.user.impl

import com.vntana.core.rest.facade.user.AbstractUserServiceFacadeUnitTest
import com.vntana.core.service.token.reset.password.dto.CreateTokenResetPasswordDto
import org.easymock.EasyMock.expect
import org.easymock.EasyMock.isA
import org.junit.Test
import java.util.*


/**
 * Created by Arman Gevorgyan.
 * Date: 12/12/19
 * Time: 3:37 PM
 */
class UserSendResetPasswordEmailServiceFacadeUnitTest : AbstractUserServiceFacadeUnitTest() {

    @Test
    fun `test when user not found`() {
        val request = restHelper.buildSendUserResetPasswordRequest()
        resetAll()
        expect(userService.findByEmail(request.email)).andReturn(Optional.empty())
        replayAll()
        userServiceFacade.sendResetPasswordEmail(request).let {
            assertBasicSuccessResultResponse(it)
        }
        verifyAll()
    }

    @Test
    fun `test when  not found`() {
        val user = userHelper.buildUserWithOrganizationOwnerRole()
        val request = restHelper.buildSendUserResetPasswordRequest(email = user.email)
        val token = tokenCommonTestHelper.buildTokenResetPassword(token = request.token, user = user)
        resetAll()
        expect(userService.findByEmail(user.email)).andReturn(Optional.of(user))
        expect(tokenResetPasswordService.create(isA(CreateTokenResetPasswordDto::class.java))).andReturn(token)
        expect(resetPasswordEmailSenderComponent.sendResetPasswordEmail(request.email, request.token))
        replayAll()
        assertBasicSuccessResultResponse(userServiceFacade.sendResetPasswordEmail(request))
        verifyAll()
    }
}
package com.vntana.core.rest.facade.user.impl

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.user.AbstractUserServiceFacadeUnitTest
import org.easymock.EasyMock.expect
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
            assertBasicErrorResultResponse(it, UserErrorResponseModel.NOT_FOUND_FOR_EMAIL)
        }
        verifyAll()
    }

    @Test
    fun `test when  not found`() {
        val email = uuid()
        val token = uuid()
        val user = userHelper.buildUserWithOrganizationOwnerRole(email = email)
        val request = restHelper.buildSendUserResetPasswordRequest(email= email, token = token)
        resetAll()
        expect(userService.findByEmail(request.email)).andReturn(Optional.of(user))
        expect(resetPasswordEmailSenderComponent.sendResetPasswordEmail(email, token))
        replayAll()
        assertBasicSuccessResultResponse(userServiceFacade.sendResetPasswordEmail(request))
        verifyAll()
    }
}
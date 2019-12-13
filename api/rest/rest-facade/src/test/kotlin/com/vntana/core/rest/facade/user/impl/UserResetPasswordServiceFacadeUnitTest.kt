package com.vntana.core.rest.facade.user.impl

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.user.AbstractUserServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 12/12/19
 * Time: 6:59 PM
 */
class UserResetPasswordServiceFacadeUnitTest : AbstractUserServiceFacadeUnitTest() {

    @Test
    fun `test when user not found`() {
        val request = restHelper.buildResetUserPasswordRequest()
        resetAll()
        expect(userService.findByEmail(request.email)).andReturn(Optional.empty())
        replayAll()
        assertBasicErrorResultResponse(userServiceFacade.resetPassword(request), UserErrorResponseModel.NOT_FOUND_FOR_EMAIL)
        verifyAll()
    }

    @Test
    fun test() {
        val newPassword = uuid()
        val email = uuid()
        val user = userHelper.buildUser(email = email)
        val updatedUser = userHelper.buildUser(email = email, password = newPassword)
        val request = restHelper.buildResetUserPasswordRequest(email = email, password = newPassword)
        resetAll()
        expect(userService.findByEmail(request.email)).andReturn(Optional.of(user))
        expect(userService.changePassword(user.uuid, newPassword)).andReturn(updatedUser)
        replayAll()
        userServiceFacade.resetPassword(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().email).isEqualTo(email)
        }
        verifyAll()
    }
}
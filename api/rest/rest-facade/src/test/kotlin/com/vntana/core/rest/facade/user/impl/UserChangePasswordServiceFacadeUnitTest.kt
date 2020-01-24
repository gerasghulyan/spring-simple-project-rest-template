package com.vntana.core.rest.facade.user.impl

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.user.AbstractUserServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 1/24/2020
 * Time: 11:04 AM
 */
class UserChangePasswordServiceFacadeUnitTest : AbstractUserServiceFacadeUnitTest() {

    @Test
    fun `test update with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { userServiceFacade.changePassword(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test update when user not exist`() {
        // test data
        val request = restHelper.buildChangeUserPasswordRequest()
        resetAll()
        // expectations
        expect(userService.existsByUuid(request.uuid)).andReturn(false)
        replayAll()
        // test scenario
        assertBasicErrorResultResponse(userServiceFacade.changePassword(request), UserErrorResponseModel.USER_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun `test update when password mismatches`() {
        // test data
        val request = restHelper.buildChangeUserPasswordRequest()
        resetAll()
        // expectations
        expect(userService.existsByUuid(request.uuid)).andReturn(true)
        expect(userService.checkPassword(request.uuid, request.oldPassword)).andReturn(false)
        replayAll()
        // test scenario
        assertBasicErrorResultResponse(userServiceFacade.changePassword(request), UserErrorResponseModel.PASSWORD_MISMATCH)
        verifyAll()
    }

    @Test
    fun `test update`() {
        // test data
        val request = restHelper.buildChangeUserPasswordRequest()
        val user = userHelper.buildUser(request.uuid)
        resetAll()
        // expectations
        expect(userService.existsByUuid(request.uuid)).andReturn(true)
        expect(userService.checkPassword(request.uuid, request.oldPassword)).andReturn(true)
        expect(userService.changePassword(request.uuid, request.newPassword)).andReturn(user)
        replayAll()
        // test scenario
        assertBasicSuccessResultResponse(userServiceFacade.changePassword(request))
        verifyAll()
    }
}
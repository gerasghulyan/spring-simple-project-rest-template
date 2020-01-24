package com.vntana.core.rest.facade.user.impl

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.user.AbstractUserServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 1/23/2020
 * Time: 5:15 PM
 */
class UserUpdateServiceFacadeUnitTest : AbstractUserServiceFacadeUnitTest() {

    @Test
    fun `test update with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { userServiceFacade.update(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test update when user not exist`() {
        // test data
        val request = restHelper.buildUpdateUserRequest()
        resetAll()
        // expectations
        expect(userService.existsByUuid(request.uuid)).andReturn(false)
        replayAll()
        // test scenario
        assertBasicErrorResultResponse(userServiceFacade.update(request), UserErrorResponseModel.USER_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun `test update`() {
        // test data
        val user = userHelper.buildUser()
        val request = restHelper.buildUpdateUserRequest(uuid = user.uuid)
        val dto = userHelper.buildUpdateUserDto(
                request.uuid,
                request.imageBlobId,
                request.fullName
        )
        resetAll()
        // expectations
        expect(userService.existsByUuid(request.uuid)).andReturn(true)
        expect(userService.update(dto)).andReturn(user)
        replayAll()
        // test scenario
        val result = userServiceFacade.update(request)
        assertBasicSuccessResultResponse(result)
        assertThat(result.response().uuid).isEqualTo(request.uuid)
        verifyAll()
    }
}
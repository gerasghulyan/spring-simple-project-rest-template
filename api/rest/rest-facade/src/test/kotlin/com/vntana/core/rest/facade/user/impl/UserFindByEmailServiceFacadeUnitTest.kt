package com.vntana.core.rest.facade.user.impl

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.user.AbstractUserServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 11:09 AM
 */
class UserFindByEmailServiceFacadeUnitTest : AbstractUserServiceFacadeUnitTest() {

    @Test
    fun `test when not found`() {
        // test data
        resetAll()
        val request = restHelper.buildFindUserByEmailRequest()
        // expectations
        expect(userService.findByEmail(request.email)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        val resultResponse = userServiceFacade.findByEmail(request)
        restHelper.assertBasicErrorResultResponse(resultResponse, UserErrorResponseModel.NOT_FOUND_FOR_EMAIL)
        verifyAll()
    }

    @Test
    fun `test when found`() {
        // test data
        resetAll()
        val request = restHelper.buildFindUserByEmailRequest()
        val user = userHelper.buildUserWithOrganizationOwnerRole()
        // expectations
        expect(userService.findByEmail(request.email)).andReturn(Optional.of(user))
        replayAll()
        // test scenario
        val resultResponse = userServiceFacade.findByEmail(request)
        restHelper.assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().isExists).isTrue()
        assertThat(resultResponse.response().uuid).isEqualTo(user.uuid)
        verifyAll()
    }

}
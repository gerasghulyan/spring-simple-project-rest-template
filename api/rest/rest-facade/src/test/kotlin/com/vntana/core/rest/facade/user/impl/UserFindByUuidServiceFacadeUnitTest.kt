package com.vntana.core.rest.facade.user.impl

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.user.AbstractUserServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 12/18/19
 * Time: 11:38 AM
 */
class UserFindByUuidServiceFacadeUnitTest : AbstractUserServiceFacadeUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertBasicErrorResultResponse(userServiceFacade.findByUuid(null), UserErrorResponseModel.MISSING_UUID)
        assertBasicErrorResultResponse(userServiceFacade.findByUuid(""), UserErrorResponseModel.MISSING_UUID)
        verifyAll()
    }

    @Test
    fun `test when user not found`() {
        val userUuid = uuid()
        resetAll()
        expect(userService.findByUuid(userUuid)).andReturn(Optional.empty())
        replayAll()
        userServiceFacade.findByUuid(userUuid).let {
            assertBasicErrorResultResponse(it, UserErrorResponseModel.NOT_FOUND_FOR_UUID)
        }
        verifyAll()
    }
    
    @Test
    fun test() {
        val user = userHelper.buildUser()
        val userUuid = user.uuid
        user.uuid = userUuid
        resetAll()
        expect(userService.findByUuid(userUuid)).andReturn(Optional.of(user))
        replayAll()
        userServiceFacade.findByUuid(userUuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().email).isEqualTo(user.email)
            assertThat(it.response().uuid).isEqualTo(user.uuid)
        }
        verifyAll()
    }
}
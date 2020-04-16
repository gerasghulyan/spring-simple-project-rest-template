package com.vntana.core.rest.facade.user.impl

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.user.AbstractUserServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 4/6/20
 * Time: 10:38 AM
 */
class UserExistsByEmailServiceFacadeUnitTest : AbstractUserServiceFacadeUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertBasicErrorResultResponse(userServiceFacade.existsByEmail(null), UserErrorResponseModel.MISSING_EMAIL)
        assertBasicErrorResultResponse(userServiceFacade.existsByEmail(emptyString()), UserErrorResponseModel.MISSING_EMAIL)
        verifyAll()
    }

    @Test
    fun `test when does not exist`() {
        resetAll()
        val email = uuid()
        expect(userService.existsByEmail(email)).andReturn(false)
        replayAll()
        userServiceFacade.existsByEmail(email).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().exists).isFalse()
        }
        verifyAll()
    }

    @Test
    fun `test when exists`() {
        resetAll()
        val email = uuid()
        expect(userService.existsByEmail(email)).andReturn(true)
        replayAll()
        userServiceFacade.existsByEmail(email).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().exists).isTrue()
        }
        verifyAll()
    }

}
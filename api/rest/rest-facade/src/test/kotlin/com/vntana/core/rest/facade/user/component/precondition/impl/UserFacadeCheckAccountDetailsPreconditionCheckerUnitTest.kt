package com.vntana.core.rest.facade.user.component.precondition.impl

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.user.component.precondition.AbstractUserFacadePreconditionCheckerComponentUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 4/8/20
 * Time: 10:19 AM
 */
class UserFacadeCheckAccountDetailsPreconditionCheckerUnitTest : AbstractUserFacadePreconditionCheckerComponentUnitTest() {

    @Test
    fun `test when user uuid is blank`() {
        resetAll()
        replayAll()
        preconditionChecker.checkAccountDetails(null).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(UserErrorResponseModel.MISSING_UUID)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        }
        preconditionChecker.checkAccountDetails(emptyString()).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(UserErrorResponseModel.MISSING_UUID)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        }
        verifyAll()
    }

    @Test
    fun `test when user not found`() {
        resetAll()
        val userUuid = uuid()
        expect(userService.existsByUuid(userUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkAccountDetails(userUuid).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(UserErrorResponseModel.NOT_FOUND_FOR_UUID)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when found`() {
        resetAll()
        val userUuid = uuid()
        expect(userService.existsByUuid(userUuid)).andReturn(true)
        replayAll()
        preconditionChecker.checkAccountDetails(userUuid).let {
            assertThat(it.isPresent).isFalse()
        }
        verifyAll()
    }
}
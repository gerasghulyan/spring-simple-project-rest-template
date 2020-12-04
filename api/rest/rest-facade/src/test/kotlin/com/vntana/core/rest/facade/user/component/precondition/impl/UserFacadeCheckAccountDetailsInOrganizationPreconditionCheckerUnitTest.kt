package com.vntana.core.rest.facade.user.component.precondition.impl

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.user.component.precondition.AbstractUserFacadePreconditionCheckerComponentUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 04.12.2020
 * Time: 14:02
 */
class UserFacadeCheckAccountDetailsInOrganizationPreconditionCheckerUnitTest : AbstractUserFacadePreconditionCheckerComponentUnitTest() {

    @Test
    fun `test invalid arguments`() {
        resetAll()
        replayAll()
        preconditionChecker.checkAccountDetails(uuid(), null).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(UserErrorResponseModel.MISSING_UUID)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        }
        preconditionChecker.checkAccountDetails(uuid(), emptyString()).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(UserErrorResponseModel.MISSING_UUID)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        }
        preconditionChecker.checkAccountDetails(emptyString(), uuid()).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(UserErrorResponseModel.MISSING_ORGANIZATION)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        }
        preconditionChecker.checkAccountDetails(null, uuid()).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(UserErrorResponseModel.MISSING_ORGANIZATION)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        }
        verifyAll()
    }

    @Test
    fun `test when user not found`() {
        resetAll()
        val userUuid = uuid()
        val organizationUuid = uuid()
        expect(userService.existsByUuid(userUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkAccountDetails(organizationUuid, userUuid).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(UserErrorResponseModel.NOT_FOUND_FOR_UUID)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when organization not found`() {
        resetAll()
        val userUuid = uuid()
        val organizationUuid = uuid()
        expect(userService.existsByUuid(userUuid)).andReturn(true)
        expect(organizationService.existsByUuid(organizationUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkAccountDetails(organizationUuid, userUuid).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(UserErrorResponseModel.NOT_FOUND_FOR_ORGANIZATION)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when found`() {
        resetAll()
        val userUuid = uuid()
        val organizationUuid = uuid()
        expect(userService.existsByUuid(userUuid)).andReturn(true)
        expect(organizationService.existsByUuid(organizationUuid)).andReturn(true)
        replayAll()
        preconditionChecker.checkAccountDetails(organizationUuid, userUuid).let {
            assertThat(it.isPresent).isFalse()
        }
        verifyAll()
    }
}
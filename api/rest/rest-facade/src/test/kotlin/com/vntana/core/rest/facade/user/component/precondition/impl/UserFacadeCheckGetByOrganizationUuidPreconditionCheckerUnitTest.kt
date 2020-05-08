package com.vntana.core.rest.facade.user.component.precondition.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.user.component.precondition.AbstractUserFacadePreconditionCheckerComponentUnitTest
import org.apache.http.HttpStatus
import org.apache.http.HttpStatus.SC_NOT_FOUND
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 4:57 PM
 */
class UserFacadeCheckGetByOrganizationUuidPreconditionCheckerUnitTest : AbstractUserFacadePreconditionCheckerComponentUnitTest() {

    @Test
    fun `test missing uuid`() {
        resetAll()
        replayAll()
        preconditionChecker.checkGetByOrganizationUuid(null).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(UserErrorResponseModel.MISSING_UUID)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        }
        preconditionChecker.checkGetByOrganizationUuid("").let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(UserErrorResponseModel.MISSING_UUID)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        }
        verifyAll()
    }

    @Test
    fun `test when organization not found`() {
        resetAll()
        val organizationUuid = uuid()
        expect(organizationService.existsByUuid(organizationUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkGetByOrganizationUuid(organizationUuid).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(UserErrorResponseModel.NOT_FOUND_FOR_ORGANIZATION)
            assertThat(it.httpStatus).isEqualTo(SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val organizationUuid = uuid()
        expect(organizationService.existsByUuid(organizationUuid)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkGetByOrganizationUuid(organizationUuid)).isEqualTo(SingleErrorWithStatus.empty<UserErrorResponseModel>())
        verifyAll()
    }
}
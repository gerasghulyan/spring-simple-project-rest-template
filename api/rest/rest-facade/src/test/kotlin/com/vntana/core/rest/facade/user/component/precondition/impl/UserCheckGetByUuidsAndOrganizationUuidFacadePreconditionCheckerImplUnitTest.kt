package com.vntana.core.rest.facade.user.component.precondition.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.facade.user.component.precondition.AbstractUserFacadePreconditionCheckerComponentUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 29.09.2020
 * Time: 16:10
 */
class UserCheckGetByUuidsAndOrganizationUuidFacadePreconditionCheckerImplUnitTest : AbstractUserFacadePreconditionCheckerComponentUnitTest() {

    @Test
    fun `test when organization not found`() {
        resetAll()
        val request = restTestHelper.buildGetByUuidsAndOrganizationUuid()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkGetByUuidsAndOrganizationUuid(request).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(UserErrorResponseModel.NOT_FOUND_FOR_ORGANIZATION)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when users not found`() {
        resetAll()
        val request = restTestHelper.buildGetByUuidsAndOrganizationUuid()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuids(request.uuids)).andReturn(false)
        replayAll()
        preconditionChecker.checkGetByUuidsAndOrganizationUuid(request).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(UserErrorResponseModel.NOT_FOUND_FOR_USERS_UUIDS)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildGetByUuidsAndOrganizationUuid()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuids(request.uuids)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkGetByUuidsAndOrganizationUuid(request)).isEqualTo(SingleErrorWithStatus.empty<UserErrorResponseModel>())
        verifyAll()
    }
}
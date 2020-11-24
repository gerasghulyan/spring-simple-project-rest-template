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
 * Created by Manuk Gharslyan.
 * Date: 5/7/20
 * Time: 4:57 PM
 */
class UserFacadeCheckGetByClientOrganizationUuidPreconditionCheckerUnitTest : AbstractUserFacadePreconditionCheckerComponentUnitTest() {

    @Test
    fun `test missing uuid`() {
        resetAll()
        replayAll()
        preconditionChecker.checkGetByClientOrganizationUuid(null).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(UserErrorResponseModel.MISSING_CLIENT)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        }
        preconditionChecker.checkGetByClientOrganizationUuid(emptyString()).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(UserErrorResponseModel.MISSING_CLIENT)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        }
        verifyAll()
    }

    @Test
    fun `test when organization not found`() {
        resetAll()
        val clientUuid = uuid()
        expect(clientService.existsByUuid(clientUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkGetByClientOrganizationUuid(clientUuid).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(UserErrorResponseModel.NOT_FOUND_FOR_CLIENT_ORGANIZATION)
            assertThat(it.httpStatus).isEqualTo(SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val clientUuid = uuid()
        expect(clientService.existsByUuid(clientUuid)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkGetByClientOrganizationUuid(clientUuid)).isEqualTo(SingleErrorWithStatus.empty<UserErrorResponseModel>())
        verifyAll()
    }
}
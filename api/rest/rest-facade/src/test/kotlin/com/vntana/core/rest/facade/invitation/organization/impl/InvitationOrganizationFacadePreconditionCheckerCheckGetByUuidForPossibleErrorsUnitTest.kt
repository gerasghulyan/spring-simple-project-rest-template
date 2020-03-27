package com.vntana.core.rest.facade.invitation.organization.impl

import com.vntana.commons.api.model.response.ErrorResponseModel
import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel
import com.vntana.core.rest.facade.invitation.organization.AbstractInvitationOrganizationFacadePreconditionCheckerFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 3:18 PM
 */
class InvitationOrganizationFacadePreconditionCheckerCheckGetByUuidForPossibleErrorsUnitTest : AbstractInvitationOrganizationFacadePreconditionCheckerFacadeUnitTest() {

    @Test
    fun `test when uuid is empty`() {
        resetAll()
        replayAll()
        preconditionChecker.checkGetByUuidForPossibleErrors(null).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
            assertThat(it.error).isEqualTo(InvitationOrganizationErrorResponseModel.MISSING_UUID)
        }
        preconditionChecker.checkGetByUuidForPossibleErrors(emptyString()).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
            assertThat(it.error).isEqualTo(InvitationOrganizationErrorResponseModel.MISSING_UUID)
        }
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val uuid = uuid()
        expect(invitationOrganizationService.existsByUuid(uuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkGetByUuidForPossibleErrors(uuid).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationOrganizationErrorResponseModel.NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val uuid = uuid()
        expect(invitationOrganizationService.existsByUuid(uuid)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkGetByUuidForPossibleErrors(uuid)).isEqualTo(SingleErrorWithStatus.empty<ErrorResponseModel>())
        verifyAll()
    }
}
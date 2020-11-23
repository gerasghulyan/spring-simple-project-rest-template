package com.vntana.core.rest.facade.invitation.user.checker.impl

import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.facade.invitation.user.checker.AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/13/2020
 * Time: 4:56 PM
 */
class InvitationUserFacadePreconditionCheckerCheckUpdateStatusForPossibleErrorsUnitTest : AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest() {

    @Test
    fun `test when invitation not found`() {
        val request = invitationUserRestTestHelper.buildUpdateInvitationUserInvitationStatusRequest()
        resetAll()
        expect(invitationUserToOrganizationService.existsByUuid(request.uuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkUpdateStatusForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVITATION_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun test() {
        val request = invitationUserRestTestHelper.buildUpdateInvitationUserInvitationStatusRequest()
        resetAll()
        expect(invitationUserToOrganizationService.existsByUuid(request.uuid)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkUpdateStatusForPossibleErrors(request).isPresent).isFalse()
        verifyAll()
    }
}
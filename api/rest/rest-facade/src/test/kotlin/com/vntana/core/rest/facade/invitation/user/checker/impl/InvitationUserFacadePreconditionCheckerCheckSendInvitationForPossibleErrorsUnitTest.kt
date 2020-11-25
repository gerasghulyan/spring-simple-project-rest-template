package com.vntana.core.rest.facade.invitation.user.checker.impl

import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.facade.invitation.user.checker.AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 12:08 PM
 */
class InvitationUserFacadePreconditionCheckerCheckSendInvitationForPossibleErrorsUnitTest : AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest() {

    @Test
    fun `test when inviter user does not exist`() {
        val request = invitationUserRestTestHelper.buildSendInvitationUserToOrganizationRequest()
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkSendInvitationForOrganizationForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when inviting organization does not exist`() {
        val request = invitationUserRestTestHelper.buildSendInvitationUserToOrganizationRequest()
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkSendInvitationForOrganizationForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVITING_ORGANIZATION_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun test() {
        val request = invitationUserRestTestHelper.buildSendInvitationUserToOrganizationRequest()
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkSendInvitationForOrganizationForPossibleErrors(request).isPresent).isFalse()
        verifyAll()
    }
}
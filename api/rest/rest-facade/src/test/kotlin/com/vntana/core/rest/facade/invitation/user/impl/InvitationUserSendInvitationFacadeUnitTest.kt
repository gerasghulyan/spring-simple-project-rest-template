package com.vntana.core.rest.facade.invitation.user.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.model.invitation.user.response.SendInvitationUserResultResponse
import com.vntana.core.rest.facade.invitation.user.AbstractInvitationUserFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 11:39 AM
 */
class InvitationUserSendInvitationFacadeUnitTest : AbstractInvitationUserFacadeUnitTest() {

    @Test
    fun `test when precondition check failed`() {
        val request = invitationUserRestTestHelper.buildSendInvitationUserRequest()
        resetAll()
        expect(preconditionChecker.checkSendInvitationForPossibleErrors(request))
                .andReturn(SingleErrorWithStatus.of(404, InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(invitationUserServiceFacade.sendInvitation(request), InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND)
        verifyAll()
    }
    
    @Test
    fun test() {
        resetAll()
        val request = invitationUserRestTestHelper.buildSendInvitationUserRequest()
        val response = SendInvitationUserResultResponse()
        expect(preconditionChecker.checkSendInvitationForPossibleErrors(request)).andReturn(SingleErrorWithStatus.empty())
        expect(invitationUserSenderComponent.sendInvitation(request)).andReturn(response)
        replayAll()
        assertThat(invitationUserServiceFacade.sendInvitation(request)).isEqualTo(response)
        verifyAll()
    }
}
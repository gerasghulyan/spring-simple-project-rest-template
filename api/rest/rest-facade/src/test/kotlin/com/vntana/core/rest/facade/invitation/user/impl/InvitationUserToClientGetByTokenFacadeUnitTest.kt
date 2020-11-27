package com.vntana.core.rest.facade.invitation.user.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.invitation.InvitationStatusModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.facade.invitation.user.AbstractInvitationUserFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 11/27/20
 * Time: 10:17 AM
 */
class InvitationUserToClientGetByTokenFacadeUnitTest : AbstractInvitationUserFacadeUnitTest() {

    @Test
    fun `test when precondition check failed`() {
        val token = uuid()
        resetAll()
        expect(preconditionChecker.checkGetByTokenInvitationToClientForPossibleErrors(token))
                .andReturn(SingleErrorWithStatus.of(404, InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN))
        replayAll()
        assertBasicErrorResultResponse(invitationUserServiceFacade.getByTokenInvitationToClient(token), InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN)
        verifyAll()
    }

    @Test
    fun test() {
        val token = uuid()
        val userInvitation = invitationUserCommonTestHelper.buildInvitationOrganizationClientUser()
        resetAll()
        expect(preconditionChecker.checkGetByTokenInvitationToClientForPossibleErrors(token)).andReturn(SingleErrorWithStatus.empty())
        expect(invitationUserToClientService.getByToken(token)).andReturn(userInvitation)
        expect(userService.existsByEmail(userInvitation.email)).andReturn(true)
        replayAll()
        val resultResponse = invitationUserServiceFacade.getByTokenInvitationToClient(token)
        assertBasicSuccessResultResponse(resultResponse)
        val response = resultResponse.response()
        assertThat(response.uuid).isEqualTo(userInvitation.uuid)
        assertThat(response.invitedUserEmail).isEqualTo(userInvitation.email)
        assertThat(response.inviterUserFullName).isEqualTo(userInvitation.inviterUser.fullName)
        assertThat(response.invitedUserExists).isEqualTo(true)
        assertThat(response.status).isEqualTo(InvitationStatusModel.valueOf(userInvitation.status.name))
        verifyAll()
    }
}
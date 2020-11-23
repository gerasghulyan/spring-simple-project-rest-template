package com.vntana.core.rest.facade.invitation.user.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.invitation.InvitationStatusModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.facade.invitation.user.AbstractInvitationUserFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 8:55 PM
 */
class InvitationUserGetByTokenFacadeUnitTest : AbstractInvitationUserFacadeUnitTest() {

    @Test
    fun `test when precondition check failed`() {
        val token = uuid()
        resetAll()
        expect(preconditionChecker.checkGetByTokenForPossibleErrors(token))
                .andReturn(SingleErrorWithStatus.of(404, InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN))
        replayAll()
        assertBasicErrorResultResponse(invitationUserServiceFacade.getByToken(token), InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN)
        verifyAll()
    }

    @Test
    fun test() {
        val token = uuid()
        val invitationUser = invitationUserCommonTestHelper.buildInvitationUserToOrganization()
        resetAll()
        expect(preconditionChecker.checkGetByTokenForPossibleErrors(token)).andReturn(SingleErrorWithStatus.empty())
        expect(invitationUserToOrganizationService.getByToken(token)).andReturn(invitationUser)
        expect(userService.existsByEmail(invitationUser.email)).andReturn(true)
        replayAll()
        val resultResponse = invitationUserServiceFacade.getByToken(token)
        assertBasicSuccessResultResponse(resultResponse)
        val response = resultResponse.response()
        assertThat(response.uuid).isEqualTo(invitationUser.uuid)
        assertThat(response.invitedUserEmail).isEqualTo(invitationUser.email)
        assertThat(response.organizationName).isEqualTo(invitationUser.organization.name)
        assertThat(response.inviterUserFullName).isEqualTo(invitationUser.inviterUser.fullName)
        assertThat(response.invitedUserExists).isEqualTo(true)
        assertThat(response.status).isEqualTo(InvitationStatusModel.valueOf(invitationUser.status.name))
        verifyAll()
    }
}
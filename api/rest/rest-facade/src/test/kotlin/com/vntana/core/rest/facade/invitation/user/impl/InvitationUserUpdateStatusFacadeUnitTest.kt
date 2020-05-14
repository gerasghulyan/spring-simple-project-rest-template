package com.vntana.core.rest.facade.invitation.user.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.model.invitation.InvitationStatusModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.facade.invitation.user.AbstractInvitationUserFacadeUnitTest
import com.vntana.core.service.invitation.user.dto.UpdateInvitationUserStatusDto
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/13/2020
 * Time: 4:46 PM
 */
class InvitationUserUpdateStatusFacadeUnitTest : AbstractInvitationUserFacadeUnitTest() {

    @Test
    fun `test when precondition check failed`() {
        val request = invitationUserRestTestHelper.buildUpdateInvitationUserInvitationStatusRequest()
        resetAll()
        expect(preconditionChecker.checkUpdateStatusForPossibleErrors(request))
                .andReturn(SingleErrorWithStatus.of(404, InvitationUserErrorResponseModel.INVITATION_NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(invitationUserServiceFacade.updateStatus(request), InvitationUserErrorResponseModel.INVITATION_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        val invitationUser = invitationUserCommonTestHelper.buildInvitationUser()
        val request = invitationUserRestTestHelper.buildUpdateInvitationUserInvitationStatusRequest(
                uuid = invitationUser.uuid,
                status = InvitationStatusModel.REJECTED
        )
        val dto = invitationUserCommonTestHelper.buildUpdateInvitationUserStatusDto(
                uuid = invitationUser.uuid,
                status = InvitationStatus.valueOf(request.status.name)
        )
        resetAll()
        expect(preconditionChecker.checkUpdateStatusForPossibleErrors(request)).andReturn(SingleErrorWithStatus.empty())
        expect(mapperFacade.map(request, UpdateInvitationUserStatusDto::class.java)).andReturn(dto)
        expect(invitationUserService.updateStatus(dto)).andReturn(invitationUser)
        replayAll()
        val resultResponse = invitationUserServiceFacade.updateStatus(request)
        assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().uuid).isNotBlank()
        verifyAll()
    }
}
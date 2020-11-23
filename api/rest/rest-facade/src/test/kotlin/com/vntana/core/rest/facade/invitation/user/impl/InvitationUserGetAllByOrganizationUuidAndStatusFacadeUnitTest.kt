package com.vntana.core.rest.facade.invitation.user.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.model.invitation.user.response.model.GetAllByStatusUserInvitationsResponseModel
import com.vntana.core.rest.facade.invitation.user.AbstractInvitationUserFacadeUnitTest
import com.vntana.core.service.invitation.user.dto.GetAllByOrganizationUuidAndStatusInvitationUsersDto
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/13/2020
 * Time: 3:58 PM
 */
class InvitationUserGetAllByOrganizationUuidAndStatusFacadeUnitTest : AbstractInvitationUserFacadeUnitTest() {

    @Test
    fun `test when precondition check failed`() {
        val request = invitationUserRestTestHelper.buildGetAllByStatusInvitationUserRequest()
        resetAll()
        expect(preconditionChecker.checkGetAllByOrganizationUuidAndStatusForPossibleErrors(request))
                .andReturn(SingleErrorWithStatus.of(404, InvitationUserErrorResponseModel.INVITING_ORGANIZATION_NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(invitationUserServiceFacade.getAllByOrganizationUuidAndStatus(request), InvitationUserErrorResponseModel.INVITING_ORGANIZATION_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        val request = invitationUserRestTestHelper.buildGetAllByStatusInvitationUserRequest()
        val dto = invitationUserCommonTestHelper.buildGetAllByOrganizationUuidAndStatusInvitationUsersDto(
                request.page, request.size, request.organizationUuid, InvitationStatus.valueOf(request.status.name)
        )
        val invitations = listOf(
                invitationUserCommonTestHelper.buildInvitationUserToOrganization(),
                invitationUserCommonTestHelper.buildInvitationUserToOrganization(),
                invitationUserCommonTestHelper.buildInvitationUserToOrganization()
        )
        resetAll()
        expect(preconditionChecker.checkGetAllByOrganizationUuidAndStatusForPossibleErrors(request)).andReturn(SingleErrorWithStatus.empty())
        expect(mapperFacade.map(request, GetAllByOrganizationUuidAndStatusInvitationUsersDto::class.java)).andReturn(dto)
        expect(invitationUserToOrganizationService.getAllByOrganizationUuidAndStatus(dto))
                .andReturn(invitationUserCommonTestHelper.buildGetAllByStatusInvitationUsersPage(invitations.size.toLong(), invitations))
        replayAll()
        val resultResponse = invitationUserServiceFacade.getAllByOrganizationUuidAndStatus(request)
        assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().totalCount()).isEqualTo(3)
        assertThat(resultResponse.response().items()).containsOnlyElementsOf(invitations.map { invitationUser ->
            GetAllByStatusUserInvitationsResponseModel(invitationUser.uuid, UserRoleModel.valueOf(invitationUser.role.name), invitationUser.email)
        })
        verifyAll()
    }
}
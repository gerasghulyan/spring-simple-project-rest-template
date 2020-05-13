package com.vntana.core.rest.facade.invitation.user.impl

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.invitation.user.response.model.GetAllByStatusUserInvitationsResponseModel
import com.vntana.core.rest.facade.invitation.user.AbstractInvitationUserFacadeUnitTest
import com.vntana.core.service.invitation.user.dto.GetAllByStatusInvitationUsersDto
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/13/2020
 * Time: 3:58 PM
 */
class InvitationUserGetAllByStatusFacadeUnitTest : AbstractInvitationUserFacadeUnitTest() {

    @Test
    fun test() {
        val request = invitationUserRestTestHelper.buildGetAllByStatusInvitationUserRequest()
        val dto = invitationUserCommonTestHelper.buildGetAllByStatusInvitationUsersDto(
                request.page, request.size, InvitationStatus.valueOf(request.status.name)
        )
        val invitations = listOf(
                invitationUserCommonTestHelper.buildInvitationUser(),
                invitationUserCommonTestHelper.buildInvitationUser(),
                invitationUserCommonTestHelper.buildInvitationUser()
        )
        resetAll()
        expect(mapperFacade.map(request, GetAllByStatusInvitationUsersDto::class.java)).andReturn(dto)
        expect(invitationUserService.getAllByStatus(dto))
                .andReturn(invitationUserCommonTestHelper.buildGetAllByStatusInvitationUsersPage(invitations.size.toLong(), invitations))
        replayAll()
        val resultResponse = invitationUserServiceFacade.getAllByStatus(request)
        assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().totalCount()).isEqualTo(3)
        assertThat(resultResponse.response().items()).containsOnlyElementsOf(invitations.map { invitationUser ->
            GetAllByStatusUserInvitationsResponseModel(invitationUser.uuid, UserRoleModel.valueOf(invitationUser.role.name), invitationUser.email)
        })
        verifyAll()
    }
}
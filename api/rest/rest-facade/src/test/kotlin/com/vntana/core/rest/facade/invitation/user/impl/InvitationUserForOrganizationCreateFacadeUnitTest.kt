package com.vntana.core.rest.facade.invitation.user.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.facade.invitation.user.AbstractInvitationUserFacadeUnitTest
import com.vntana.core.service.invitation.user.dto.CreateInvitationForOrganizationUserDto
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 6:12 PM
 */
class InvitationUserForOrganizationCreateFacadeUnitTest : AbstractInvitationUserFacadeUnitTest() {

    @Test
    fun `test when precondition check failed`() {
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForOrganizationRequest()
        resetAll()
        expect(preconditionChecker.checkCreateInvitationForOrganizationForPossibleErrors(request))
                .andReturn(SingleErrorWithStatus.of(404, InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(invitationUserServiceFacade.createInvitationForOrganization(request), InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForOrganizationRequest(organizationUuid = organization.uuid)
        val getAllDto = invitationUserCommonTestHelper.buildGetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto(
                email = request.email,
                organizationUuid = request.organizationUuid
        )
        val dto = invitationUserCommonTestHelper.buildCreateInvitationUserForOrganizationDto(
                email = request.email,
                inviterUserUuid = request.inviterUserUuid,
                organizationUuid = request.organizationUuid
        )
        val userInvitations = listOf(
                invitationUserCommonTestHelper.buildInvitationUserToOrganization(email = request.email, organization = organization)
        )
        val updatedUserInvitation = invitationUserCommonTestHelper.buildInvitationUserToOrganization()
        val tokenInvitationUser = tokenInvitationUserCommonTestHelper.buildTokenInvitationUserToOrganization(invitationOrganizationUser = updatedUserInvitation)
        val updateDto = invitationUserCommonTestHelper.buildUpdateInvitationUserStatusDto(uuid = userInvitations[0].uuid)
        resetAll()
        expect(preconditionChecker.checkCreateInvitationForOrganizationForPossibleErrors(request)).andReturn(SingleErrorWithStatus.empty())
        expect(mapperFacade.map(request, CreateInvitationForOrganizationUserDto::class.java)).andReturn(dto)
        expect(invitationUserToOrganizationService.getAllByEmailAndOrganizationUuidAndStatusOrderByCreatedDesc(getAllDto)).andReturn(userInvitations)
        expect(invitationUserToOrganizationService.updateStatus(updateDto)).andReturn(updatedUserInvitation)
        expect(tokenInvitationUserService.findByInvitationUserUuid(updatedUserInvitation.uuid)).andReturn(Optional.of(tokenInvitationUser))
        expect(invitationUserToOrganizationService.create(dto)).andReturn(userInvitations[0])
        replayAll()
        assertBasicSuccessResultResponse(invitationUserServiceFacade.createInvitationForOrganization(request))
        verifyAll()
    }
}
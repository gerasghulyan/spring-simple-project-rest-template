package com.vntana.core.rest.facade.invitation.user.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.facade.invitation.user.AbstractInvitationUserFacadeUnitTest
import org.easymock.EasyMock.anyObject
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 6:12 PM
 */
class InvitationUserCreateFacadeUnitTest : AbstractInvitationUserFacadeUnitTest() {

    @Test
    fun `test when precondition check failed`() {
        val request = invitationUserRestTestHelper.buildCreateInvitationUserRequest()
        resetAll()
        expect(preconditionChecker.checkCreateForPossibleErrors(request))
                .andReturn(SingleErrorWithStatus.of(404, InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(invitationUserServiceFacade.create(request), InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val request = invitationUserRestTestHelper.buildCreateInvitationUserRequest(organizationUuid = organization.uuid)
        val getAllDto = invitationUserCommonTestHelper.buildGetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto(
                email = request.email,
                organizationUuid = request.organizationUuid
        )
        val dto = invitationUserCommonTestHelper.buildCreateInvitationUserDto(
                email = request.email,
                inviterUserUuid = request.inviterUserUuid,
                organizationUuid = request.organizationUuid
        )
        val userInvitations = listOf(
                invitationUserCommonTestHelper.buildInvitationUser(email = request.email, organization = organization)
        )
        val updateDto = invitationUserCommonTestHelper.buildUpdateInvitationUserStatusDto(uuid = userInvitations[0].uuid)
        resetAll()
        expect(preconditionChecker.checkCreateForPossibleErrors(request)).andReturn(SingleErrorWithStatus.empty())
        expect(invitationUserService.getByEmailAndOrganizationUuidAndStatusOrderByCreatedDesc(getAllDto)).andReturn(userInvitations)
        expect(invitationUserService.updateStatus(updateDto)).andReturn(anyObject())
        expect(invitationUserService.create(dto)).andReturn(userInvitations[0])
        replayAll()
        assertBasicSuccessResultResponse(invitationUserServiceFacade.create(request))
        verifyAll()
    }
}
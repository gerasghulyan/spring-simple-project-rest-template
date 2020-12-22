package com.vntana.core.rest.facade.invitation.user.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.domain.user.UserRole
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.facade.invitation.user.AbstractInvitationUserFacadeUnitTest
import com.vntana.core.service.user.role.dto.UserGrantClientRoleDto
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.easymock.EasyMock.isA
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 11/25/20
 * Time: 5:49 PM
 */
class InvitationUserToClientAcceptFacadeUnitTest : AbstractInvitationUserFacadeUnitTest() {

    @Test
    fun `test when precondition failed`() {
        resetAll()
        val request = invitationUserRestTestHelper.buildAcceptInvitationUserRequest()
        val error = SingleErrorWithStatus.of(404, InvitationUserErrorResponseModel.INVALID_INVITATION_TOKEN)
        expect(preconditionChecker.checkAcceptForClientForPossibleErrors(request)).andReturn(error)
        replayAll()
        invitationUserServiceFacade.acceptInvitationToClient(request).let {
            assertBasicErrorResultResponse(it, error.error)
            assertThat(it.httpStatusCode).isEqualTo(error.httpStatus)
        }
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = invitationUserRestTestHelper.buildAcceptInvitationUserRequest()
        val userInvitation = invitationUserCommonTestHelper.buildInvitationOrganizationClientUser(userRole = UserRole.CLIENT_ORGANIZATION_CONTENT_MANAGER)
        val client = userInvitation.clientOrganization
        val userInvitationToken = tokenInvitationUserCommonTestHelper.buildTokenInvitationUserToClient(invitationOrganizationUser = userInvitation)
        val user = userCommonTestHelper.buildUser()
        val updateUserInvitationStatusDto = invitationUserCommonTestHelper.buildUpdateInvitationUserStatusDto(
                uuid = userInvitation.uuid,
                status = InvitationStatus.ACCEPTED
        )

        val userInvitationWithUpdatedStatus = invitationUserCommonTestHelper.buildInvitationOrganizationClientUser(
                email = userInvitation.email,
                status = InvitationStatus.ACCEPTED,
                inviterUser = userInvitation.inviterUser,
                userRole = UserRole.CLIENT_ORGANIZATION_CONTENT_MANAGER,
                clientOrganization = userInvitation.clientOrganization
        )

        val clientAdminRole = userRoleCommonTestHelper.buildUserClientAdminRole()
        expect(preconditionChecker.checkAcceptForClientForPossibleErrors(request)).andReturn(SingleErrorWithStatus.empty())
        expect(tokenInvitationUserService.getByClientInvitationToken(request.token)).andReturn(userInvitationToken)
        expect(userService.getByEmail(userInvitationToken.userInvitation.email)).andReturn(user)
        expect(userRoleService.grantClientRole(isA(UserGrantClientRoleDto::class.java))).andReturn(clientAdminRole)
        expect(invitationUserToClientService.updateStatus(updateUserInvitationStatusDto)).andReturn(userInvitationWithUpdatedStatus)
        expect(tokenService.findByTokenAndExpire(request.token)).andReturn(userInvitationToken)
        replayAll()
        invitationUserServiceFacade.acceptInvitationToClient(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().userRoleModel).isEqualTo(UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)
            assertThat(it.response().userUuid).isEqualTo(user.uuid)
            assertThat(it.response().clientUuid).isEqualTo(client.uuid)
            assertThat(it.response().organizationUuid).isEqualTo(client.organization.uuid)
        }
        verifyAll()
    }
}
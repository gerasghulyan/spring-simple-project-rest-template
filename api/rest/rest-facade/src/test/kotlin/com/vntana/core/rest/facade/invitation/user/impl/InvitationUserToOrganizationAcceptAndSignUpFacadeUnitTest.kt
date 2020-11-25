package com.vntana.core.rest.facade.invitation.user.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.facade.invitation.user.AbstractInvitationUserFacadeUnitTest
import com.vntana.core.service.user.role.dto.UserGrantOrganizationRoleDto
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/15/20
 * Time: 5:29 PM
 */
class InvitationUserToOrganizationAcceptAndSignUpFacadeUnitTest : AbstractInvitationUserFacadeUnitTest() {

    @Test
    fun `test when precondition failed`() {
        resetAll()
        val request = invitationUserRestTestHelper.buildAcceptInvitationUserAndSignUpRequest()
        val error = SingleErrorWithStatus.of(404, InvitationUserErrorResponseModel.INVALID_INVITATION_TOKEN)
        expect(preconditionChecker.checkAcceptAndSignInvitationToOrganizationUpForPossibleErrors(request)).andReturn(error)
        replayAll()
        invitationUserServiceFacade.acceptInvitationToOrganizationAndSignUp(request).let {
            assertBasicErrorResultResponse(it, error.error)
            assertThat(it.httpStatusCode).isEqualTo(error.httpStatus)
        }
        verifyAll()
    }

    @Test
    fun test() {
        val request = invitationUserRestTestHelper.buildAcceptInvitationUserAndSignUpRequest()
        val tokenInvitationUser = tokenInvitationUserCommonTestHelper.buildTokenInvitationUserToOrganization()
        val invitationUser = tokenInvitationUser.invitationUser
        val dto = userCommonTestHelper.buildCreateUserDto(request.newUserFullName, invitationUser.email, request.password)
        val user = userCommonTestHelper.buildUser()
        val adminRole = userRoleCommonTestHelper.buildUserOrganizationAdminRole()
        val updateInvitationUserStatusDto = invitationUserCommonTestHelper.buildUpdateInvitationUserStatusDto(
                uuid = invitationUser.uuid,
                status = InvitationStatus.ACCEPTED
        )
        expect(preconditionChecker.checkAcceptAndSignInvitationToOrganizationUpForPossibleErrors(request)).andReturn(SingleErrorWithStatus.empty())
        expect(tokenInvitationUserService.getByOrganizationInvitationToken(request.token)).andReturn(tokenInvitationUser)
        expect(userService.create(dto)).andReturn(user)
        expect(userService.makeVerified(invitationUser.email)).andReturn(user)
        expect(userRoleService.grantOrganizationAdminRole(UserGrantOrganizationRoleDto(user.uuid, invitationUser.organization.uuid))).andReturn(adminRole)
        expect(invitationUserToOrganizationService.updateStatus(updateInvitationUserStatusDto)).andReturn(invitationUser)
        expect(tokenService.findByTokenAndExpire(request.token)).andReturn(tokenInvitationUser)
        replayAll()
        invitationUserServiceFacade.acceptInvitationToOrganizationAndSignUp(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().userRoleModel).isEqualTo(UserRoleModel.ORGANIZATION_ADMIN)
            assertThat(it.response().userUuid).isEqualTo(user.uuid)
            assertThat(it.response().organizationUuid).isEqualTo(invitationUser.organization.uuid)
        }
        verifyAll()
    }
}
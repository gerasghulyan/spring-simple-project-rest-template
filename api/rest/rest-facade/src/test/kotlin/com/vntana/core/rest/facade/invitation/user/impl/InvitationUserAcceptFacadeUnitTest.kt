package com.vntana.core.rest.facade.invitation.user.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.domain.user.UserRole
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.facade.invitation.user.AbstractInvitationUserFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 5:49 PM
 */
class InvitationUserAcceptFacadeUnitTest : AbstractInvitationUserFacadeUnitTest() {

    @Test
    fun `test when precondition failed`() {
        resetAll()
        val request = invitationUserRestTestHelper.buildAcceptInvitationUserRequest()
        val error = SingleErrorWithStatus.of(404, InvitationUserErrorResponseModel.INVALID_INVITATION_TOKEN)
        expect(preconditionChecker.checkAcceptForPossibleErrors(request)).andReturn(error)
        replayAll()
        invitationUserServiceFacade.accept(request).let {
            assertBasicErrorResultResponse(it, error.error)
            assertThat(it.httpStatusCode).isEqualTo(error.httpStatus)
        }
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = invitationUserRestTestHelper.buildAcceptInvitationUserRequest()
        val invitationUser = invitationUserCommonTestHelper.buildInvitationUser(userRole = UserRole.ORGANIZATION_ADMIN)
        val organization = invitationUser.organization
        val tokenInvitationUser = tokenInvitationUserCommonTestHelper.buildTokenInvitationUser(invitationUser = invitationUser)
        val user = userCommonTestHelper.buildUser()
        val grantOrganizationRoleDto = userRoleCommonTestHelper.buildUserGrantOrganizationRoleDto(userUuid = user.uuid, organizationUuid = organization.uuid)
        val updateInvitationUserStatusDto = invitationUserCommonTestHelper.buildUpdateInvitationUserStatusDto(
                uuid = invitationUser.uuid,
                status = InvitationStatus.ACCEPTED
        )
        val adminRole = userRoleCommonTestHelper.buildUserOrganizationAdminRole()
        expect(preconditionChecker.checkAcceptForPossibleErrors(request)).andReturn(SingleErrorWithStatus.empty())
        expect(tokenInvitationUserService.getByToken(request.token)).andReturn(tokenInvitationUser)
        expect(userService.getByEmail(tokenInvitationUser.invitationUser.email)).andReturn(user)
        expect(userRoleService.grantOrganizationAdminRole(grantOrganizationRoleDto)).andReturn(adminRole)
        expect(invitationUserService.updateStatus(updateInvitationUserStatusDto)).andReturn(invitationUser)
        expect(tokenService.findByTokenAndExpire(request.token)).andReturn(tokenInvitationUser)
        replayAll()
        invitationUserServiceFacade.accept(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().userRoleModel).isEqualTo(UserRoleModel.ORGANIZATION_ADMIN)
            assertThat(it.response().userUuid).isEqualTo(user.uuid)
            assertThat(it.response().organizationUuid).isEqualTo(organization.uuid)
        }
        verifyAll()
    }
}
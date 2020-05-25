package com.vntana.core.rest.facade.invitation.user.checker.impl

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.facade.invitation.user.checker.AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 5:53 PM
 */
class InvitationUserFacadePreconditionCheckerCheckCreateForPossibleErrorsUnitTest : AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest() {

    @Test
    fun `test when invited role is owner`() {
        val request = invitationUserRestTestHelper.buildCreateInvitationUserRequest(userRole = UserRoleModel.ORGANIZATION_OWNER)
        resetAll()
        replayAll()
        preconditionChecker.checkCreateForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVITED_USER_ROLE_COULD_NOT_BE_ORGANIZATION_OWNER)
        }
        verifyAll()
    }
    
    @Test
    fun `test when inviter user does not exist`() {
        val request = invitationUserRestTestHelper.buildCreateInvitationUserRequest()
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkCreateForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when organization does not exist`() {
        val request = invitationUserRestTestHelper.buildCreateInvitationUserRequest()
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkCreateForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVITING_ORGANIZATION_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when invited user already is a part of organization`() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val user = userCommonTestHelper.buildUserWithOrganizationOwnerRole(organization = organization)
        val request = invitationUserRestTestHelper.buildCreateInvitationUserRequest(organizationUuid = organization.uuid, email = user.email)
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.findByEmailAndOrganizationUuid(request.email, request.organizationUuid)).andReturn(Optional.of(user))
        replayAll()
        preconditionChecker.checkCreateForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.USER_ALREADY_PART_OF_ORGANIZATION)
        }
        verifyAll()
    }

    @Test
    fun test() {
        val request = invitationUserRestTestHelper.buildCreateInvitationUserRequest()
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.findByEmailAndOrganizationUuid(request.email, request.organizationUuid)).andReturn(Optional.empty())
        replayAll()
        assertThat(preconditionChecker.checkCreateForPossibleErrors(request).isPresent).isFalse()
        verifyAll()
    }
}
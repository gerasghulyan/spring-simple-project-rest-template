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
class InvitationUserFacadePreconditionCheckerCheckCreateForOrganizationForPossibleErrorsUnitTest : AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest() {

    @Test
    fun `test when invited role is owner`() {
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForOrganizationRequest(userRole = UserRoleModel.ORGANIZATION_OWNER)
        resetAll()
        replayAll()
        preconditionChecker.checkCreateInvitationForOrganizationForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVITED_USER_ROLE_COULD_NOT_BE_ORGANIZATION_OWNER)
        }
        verifyAll()
    }
    
    @Test
    fun `test when inviter user does not exist`() {
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForOrganizationRequest()
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkCreateInvitationForOrganizationForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when organization does not exist`() {
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForOrganizationRequest()
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkCreateInvitationForOrganizationForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVITING_ORGANIZATION_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when invited user already is a part of organization`() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val user = userCommonTestHelper.buildUserWithOrganizationOwnerRole(organization = organization)
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForOrganizationRequest(organizationUuid = organization.uuid, email = user.email)
        val organizationAdminRole = userRoleCommonTestHelper.buildUserOrganizationAdminRole()
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.findByEmail(request.email)).andReturn(Optional.of(user))
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, user.uuid)).andReturn(Optional.of(organizationAdminRole))
        replayAll()
        preconditionChecker.checkCreateInvitationForOrganizationForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.USER_ALREADY_HAS_ROLE_IN_ORGANIZATION)
        }
        verifyAll()
    }

    @Test
    fun `test when invited user already is a part of client`() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val user = userCommonTestHelper.buildUserWithOrganizationOwnerRole(organization = organization)
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForOrganizationRequest(organizationUuid = organization.uuid, email = user.email)
        val clientAdminRole = userRoleCommonTestHelper.buildUserClientAdminRole()
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.findByEmail(request.email)).andReturn(Optional.of(user))
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, user.uuid)).andReturn(Optional.empty())
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, user.uuid)).andReturn(listOf(clientAdminRole))
        replayAll()
        preconditionChecker.checkCreateInvitationForOrganizationForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.USER_ALREADY_HAS_ROLE_IN_CLIENT)
        }
        verifyAll()
    }

    @Test
    fun test() {
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForOrganizationRequest()
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.findByEmail(request.email)).andReturn(Optional.empty())
        replayAll()
        assertThat(preconditionChecker.checkCreateInvitationForOrganizationForPossibleErrors(request).isPresent).isFalse()
        verifyAll()
    }
}
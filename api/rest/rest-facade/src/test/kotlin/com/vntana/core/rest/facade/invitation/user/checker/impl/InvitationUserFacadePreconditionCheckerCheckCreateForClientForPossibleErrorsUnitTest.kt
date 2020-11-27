package com.vntana.core.rest.facade.invitation.user.checker.impl

import com.vntana.core.domain.user.UserOrganizationOwnerRole
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.model.invitation.user.request.SingleUserInvitationToClientRequestModel
import com.vntana.core.rest.facade.invitation.user.checker.AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions
import org.easymock.EasyMock.eq
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Diana Gevorgyan
 * Date: 11/11/20
 * Time: 10:13 AM
 */
class InvitationUserFacadePreconditionCheckerCheckCreateForClientForPossibleErrorsUnitTest : AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest() {
    
    @Test
    fun `test when invited user already in organization`() {
        val email = uuid()
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest(email = email)
        val alreadyInvitedUser = userCommonTestHelper.buildUser(email = email)
        resetAll()
        expect(userService.findByEmailAndOrganizationUuid(eq(request.email), eq(request.organizationUuid))).andReturn(Optional.of(alreadyInvitedUser))
        replayAll()
        preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).let {
            Assertions.assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            Assertions.assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVITED_USER_ALREADY_EXISTS_IN_ORGANIZATION)
        }
        verifyAll()
    }

    @Test
    fun `test when inviter user does not exist`() {
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest()
        resetAll()
        expect(userService.findByEmailAndOrganizationUuid(eq(request.email), eq(request.organizationUuid))).andReturn(Optional.empty())
        expect(userService.existsByUuid(eq(request.inviterUserUuid))).andReturn(false)
        replayAll()
        preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).let {
            Assertions.assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            Assertions.assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when organization does not exist`() {
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest()
        resetAll()
        expect(userService.findByEmailAndOrganizationUuid(eq(request.email), eq(request.organizationUuid))).andReturn(Optional.empty())
        expect(userService.existsByUuid(eq(request.inviterUserUuid))).andReturn(true)
        expect(organizationService.existsByUuid(eq(request.organizationUuid))).andReturn(false)
        replayAll()
        preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).let {
            Assertions.assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            Assertions.assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVITING_ORGANIZATION_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when sending wrong user role`() {
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val inviter = userCommonTestHelper.buildUserWithClientAdminRole(clientOrganization = clientOrganization)
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest(
                inviterUserUuid = inviter.uuid,
                userRoleRequestModels = listOf(SingleUserInvitationToClientRequestModel(clientOrganization.uuid, UserRoleModel.ORGANIZATION_ADMIN))
        )
        resetAll()
        expect(userService.findByEmailAndOrganizationUuid(eq(request.email), eq(request.organizationUuid))).andReturn(Optional.empty())
        expect(userService.existsByUuid(eq(request.inviterUserUuid))).andReturn(true)
        expect(organizationService.existsByUuid(eq(request.organizationUuid))).andReturn(true)
        replayAll()
        preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).let {
            Assertions.assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            Assertions.assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INCORRECT_PERMISSIONS)
        }
        verifyAll()
    }

    @Test
    fun `test when client does not exist`() {
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val inviter = userCommonTestHelper.buildUserWithClientAdminRole(clientOrganization = clientOrganization)
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest(
                inviterUserUuid = inviter.uuid,
                userRoleRequestModels = listOf(SingleUserInvitationToClientRequestModel(uuid(), UserRoleModel.CLIENT_ORGANIZATION_VIEWER))
        )
        resetAll()
        expect(userService.findByEmailAndOrganizationUuid(eq(request.email), eq(request.organizationUuid))).andReturn(Optional.empty())
        expect(userService.existsByUuid(eq(request.inviterUserUuid))).andReturn(true)
        expect(organizationService.existsByUuid(eq(request.organizationUuid))).andReturn(true)
        expect(organizationClientService.existsByUuid(eq(request.invitations[0].clientUuid))).andReturn(false).once()
        replayAll()
        preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).let {
            Assertions.assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            Assertions.assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INCORRECT_PERMISSIONS)
        }
        verifyAll()
    }
    
    @Test
    fun `test when inviter is super admin`() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization(organization = organization)
        val inviter = userCommonTestHelper.buildUserWithWithSuperAdminRole()
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest(
                organizationUuid = organization.uuid,
                inviterUserUuid = inviter.uuid,
                userRoleRequestModels = listOf(SingleUserInvitationToClientRequestModel(clientOrganization.uuid, UserRoleModel.CLIENT_ORGANIZATION_VIEWER))
        )
        resetAll()
        expect(userService.findByEmailAndOrganizationUuid(eq(request.email), eq(request.organizationUuid))).andReturn(Optional.empty())
        expect(userService.existsByUuid(eq(request.inviterUserUuid))).andReturn(true)
        expect(organizationService.existsByUuid(eq(request.organizationUuid))).andReturn(true)
        expect(organizationClientService.existsByUuid(eq(clientOrganization.uuid))).andReturn(true).once()
        expect(userService.getByUuid(eq(inviter.uuid))).andReturn(inviter)
        replayAll()
        Assertions.assertThat(preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).isPresent).isFalse()
        verifyAll()
    }

    @Test
    fun `test when inviter is organization level user`() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization(organization = organization)
        val inviter = userCommonTestHelper.buildUserWithOrganizationAdminRole(organization = organization)
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest(
                organizationUuid = organization.uuid,
                inviterUserUuid = inviter.uuid,
                userRoleRequestModels = listOf(SingleUserInvitationToClientRequestModel(clientOrganization.uuid, UserRoleModel.CLIENT_ORGANIZATION_VIEWER))
        )
        resetAll()
        expect(userService.findByEmailAndOrganizationUuid(eq(request.email), eq(request.organizationUuid))).andReturn(Optional.empty())
        expect(userService.existsByUuid(eq(request.inviterUserUuid))).andReturn(true)
        expect(organizationService.existsByUuid(eq(request.organizationUuid))).andReturn(true)
        expect(organizationClientService.existsByUuid(eq(clientOrganization.uuid))).andReturn(true).once()
        expect(userService.getByUuid(eq(inviter.uuid))).andReturn(inviter)
        expect(userRoleService.findByOrganizationAndUser(eq(organization.uuid), eq(inviter.uuid))).andReturn(Optional.of(UserOrganizationOwnerRole(inviter, organization)))
        replayAll()
        Assertions.assertThat(preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).isPresent).isFalse()
        verifyAll()
    }

    @Test
    fun `test when client does not belong to inviter clients`() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val inviter = userCommonTestHelper.buildUserWithClientAdminRole(clientOrganization = clientOrganization)
        val inviterPermissions = userRoleCommonTestHelper.buildUserClientAdminRole(inviter, clientOrganization)
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest(
                organizationUuid = organization.uuid,
                inviterUserUuid = inviter.uuid,
                userRoleRequestModels = listOf(SingleUserInvitationToClientRequestModel(uuid(), UserRoleModel.CLIENT_ORGANIZATION_VIEWER))
        )
        resetAll()
        expect(userService.findByEmailAndOrganizationUuid(eq(request.email), eq(request.organizationUuid))).andReturn(Optional.empty())
        expect(userService.existsByUuid(eq(request.inviterUserUuid))).andReturn(true)
        expect(organizationService.existsByUuid(eq(request.organizationUuid))).andReturn(true)
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(eq(request.organizationUuid), eq(request.inviterUserUuid))).andReturn(listOf(inviterPermissions))
        expect(organizationClientService.existsByUuid(eq(request.invitations[0].clientUuid))).andReturn(true).once()
        expect(userService.getByUuid(eq(inviter.uuid))).andReturn(inviter)
        expect(userRoleService.findByOrganizationAndUser(eq(organization.uuid), eq(inviter.uuid))).andReturn(Optional.empty())
        replayAll()
        preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).let {
            Assertions.assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            Assertions.assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INCORRECT_PERMISSIONS)
        }
        verifyAll()
    }

    @Test
    fun test() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val inviter = userCommonTestHelper.buildUserWithClientAdminRole(clientOrganization = clientOrganization)
        val inviterPermissions = userRoleCommonTestHelper.buildUserClientAdminRole(inviter, clientOrganization)
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest(
                organizationUuid = organization.uuid,
                inviterUserUuid = inviter.uuid,
                userRoleRequestModels = listOf(SingleUserInvitationToClientRequestModel(inviterPermissions.clientOrganization.uuid, UserRoleModel.CLIENT_ORGANIZATION_VIEWER))
        )
        resetAll()
        expect(userService.findByEmailAndOrganizationUuid(eq(request.email), eq(request.organizationUuid))).andReturn(Optional.empty())
        expect(userService.existsByUuid(eq(request.inviterUserUuid))).andReturn(true)
        expect(organizationService.existsByUuid(eq(request.organizationUuid))).andReturn(true)
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(eq(request.organizationUuid), eq(request.inviterUserUuid))).andReturn(listOf(inviterPermissions))
        expect(organizationClientService.existsByUuid(request.invitations[0].clientUuid)).andReturn(true).once()
        expect(userRoleService.findByOrganizationAndUser(eq(organization.uuid), eq(inviter.uuid))).andReturn(Optional.empty())
        expect(userService.getByUuid(eq(inviter.uuid))).andReturn(inviter)
        expect(userRolesPermissionsCheckerComponent.isPermittedToInvite(UserRoleModel.CLIENT_ORGANIZATION_ADMIN, UserRoleModel.CLIENT_ORGANIZATION_VIEWER)).andReturn(true).once()
        replayAll()
        Assertions.assertThat(preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).isPresent).isFalse()
        verifyAll()
    }
}
package com.vntana.core.rest.facade.invitation.user.checker.impl

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.facade.invitation.user.checker.AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 11/11/20
 * Time: 10:13 AM
 */
class InvitationUserFacadePreconditionCheckerCheckCreateForClientForPossibleErrorsUnitTest : AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest() {

    @Test
    fun `test when inviter user does not exist`() {
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest()
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(false)
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
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(false)
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
        val inviterPermissions = userRoleCommonTestHelper.buildUserClientAdminRole(inviter, clientOrganization)
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest(
                inviterUserUuid = inviter.uuid,
                userRoles = mapOf(Pair(inviterPermissions.clientOrganization.uuid, UserRoleModel.ORGANIZATION_ADMIN))
        )
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.inviterUserUuid)).andReturn(listOf(inviterPermissions))
        replayAll()
        preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).let {
            Assertions.assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            Assertions.assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.WRONG_PERMISSIONS)
        }
        verifyAll()
    }

    @Test
    fun `test when client does not exist`() {
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val inviter = userCommonTestHelper.buildUserWithClientAdminRole(clientOrganization = clientOrganization)
        val inviterPermissions = userRoleCommonTestHelper.buildUserClientAdminRole(inviter, clientOrganization)
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest(
                inviterUserUuid = inviter.uuid,
                userRoles = mapOf(Pair(uuid(), UserRoleModel.CLIENT_ORGANIZATION_VIEWER))
        )
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.inviterUserUuid)).andReturn(listOf(inviterPermissions))
        expect(organizationClientService.existsByUuid(request.userRoles.keys.elementAt(0))).andReturn(false).once()
        replayAll()
        preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).let {
            Assertions.assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            Assertions.assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.WRONG_PERMISSIONS)
        }
        verifyAll()
    }

    @Test
    fun `test when client does not belong to inviter clients`() {
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val inviter = userCommonTestHelper.buildUserWithClientAdminRole(clientOrganization = clientOrganization)
        val inviterPermissions = userRoleCommonTestHelper.buildUserClientAdminRole(inviter, clientOrganization)
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest(
                inviterUserUuid = inviter.uuid,
                userRoles = mapOf(Pair(uuid(), UserRoleModel.CLIENT_ORGANIZATION_VIEWER))
        )
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.inviterUserUuid)).andReturn(listOf(inviterPermissions))
        expect(organizationClientService.existsByUuid(request.userRoles.keys.elementAt(0))).andReturn(true).once()
        replayAll()
        preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).let {
            Assertions.assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            Assertions.assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.WRONG_PERMISSIONS)
        }        
        verifyAll()
    }


    @Test
    fun test() {
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val inviter = userCommonTestHelper.buildUserWithClientAdminRole(clientOrganization = clientOrganization)
        val inviterPermissions = userRoleCommonTestHelper.buildUserClientAdminRole(inviter, clientOrganization)
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest(
                inviterUserUuid = inviter.uuid,
                userRoles = mapOf(Pair(inviterPermissions.clientOrganization.uuid, UserRoleModel.CLIENT_ORGANIZATION_VIEWER))
        )
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.inviterUserUuid)).andReturn(listOf(inviterPermissions))
        expect(organizationClientService.existsByUuid(request.userRoles.keys.elementAt(0))).andReturn(true).once()
        expect(userRolesPermissionsCheckerComponent.isPermittedToInvite(UserRoleModel.CLIENT_ORGANIZATION_ADMIN, UserRoleModel.CLIENT_ORGANIZATION_VIEWER)).andReturn(true).once()
        replayAll()
        Assertions.assertThat(preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).isPresent).isFalse()
        verifyAll()
    }
}
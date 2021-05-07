package com.vntana.core.rest.facade.invitation.user.checker.impl

import com.vntana.core.domain.user.UserOrganizationOwnerRole
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.model.invitation.user.request.SingleUserInvitationToClientRequestModel
import com.vntana.core.rest.facade.invitation.user.checker.AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.eq
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Diana Gevorgyan
 * Date: 11/11/20
 * Time: 10:13 AM
 */
class InvitationUserFacadePreconditionCheckerCheckCreateForClientForPossibleErrorsUnitTest :
    AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest() {

    @Test
    fun `test when inviter user does not exist`() {
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest()
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND)
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
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVITING_ORGANIZATION_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when client does not exist`() {
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val inviter = userCommonTestHelper.buildUserWithClientAdminRole(clientOrganization = clientOrganization)
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest(
            inviterUserUuid = inviter.uuid,
            userRoleRequestModels = listOf(
                SingleUserInvitationToClientRequestModel(
                    uuid(),
                    UserRoleModel.CLIENT_ORGANIZATION_VIEWER
                )
            )
        )
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(clientOrganizationService.existsByUuid(request.invitations[0].clientUuid)).andReturn(false).once()
        replayAll()
        preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INCORRECT_PERMISSIONS)
        }
        verifyAll()
    }

    @Test
    fun `test when invited user already has organization level role`() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val inviter = userCommonTestHelper.buildUserWithClientAdminRole(clientOrganization = clientOrganization)
        val invited = userCommonTestHelper.buildUserWithOrganizationAdminRole(organization = organization)
        val invitedUserOrganizationLevelRole =
            userRoleCommonTestHelper.buildUserOrganizationAdminRole(user = invited, organization = organization)
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest(
            organizationUuid = organization.uuid,
            email = invited.email,
            inviterUserUuid = inviter.uuid,
            userRoleRequestModels = listOf(
                SingleUserInvitationToClientRequestModel(
                    clientOrganization.uuid,
                    UserRoleModel.CLIENT_ORGANIZATION_VIEWER
                )
            )
        )
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(clientOrganizationService.existsByUuid(eq(clientOrganization.uuid))).andReturn(true).once()
        expect(userService.findByEmail(eq(request.email))).andReturn(
            Optional.of(invited)
        )
        expect(userRoleService.findByOrganizationAndUser(eq(request.organizationUuid), eq(invited.uuid))).andReturn(
            Optional.of(invitedUserOrganizationLevelRole)
        )
        replayAll()
        preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.USER_ALREADY_HAS_ROLE_IN_ORGANIZATION)
        }
        verifyAll()
    }

    @Test
    fun `test when invited user already has role in one of clients`() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val inviter = userCommonTestHelper.buildUserWithClientAdminRole(clientOrganization = clientOrganization)
        val invited = userCommonTestHelper.buildUserWithOrganizationAdminRole(organization = organization)
        val invitedUserClientLevelRole = userRoleCommonTestHelper.buildUserClientContentManagerRole(
            user = invited,
            clientOrganization = clientOrganization
        )
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest(
            organizationUuid = organization.uuid,
            email = invited.email,
            inviterUserUuid = inviter.uuid,
            userRoleRequestModels = listOf(
                SingleUserInvitationToClientRequestModel(
                    clientOrganization.uuid,
                    UserRoleModel.CLIENT_ORGANIZATION_VIEWER
                )
            )
        )
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(clientOrganizationService.existsByUuid(eq(clientOrganization.uuid))).andReturn(true).once()
        expect(userService.findByEmail(eq(request.email))).andReturn(
            Optional.of(invited)
        )
        expect(userRoleService.findByOrganizationAndUser(eq(request.organizationUuid), eq(invited.uuid))).andReturn(
            Optional.empty()
        )
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, invited.uuid)).andReturn(
                listOf(invitedUserClientLevelRole)
        )
        replayAll()
        preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.USER_ALREADY_HAS_ROLE_IN_CLIENT)
        }
        verifyAll()
    }

    @Test
    fun `test when inviting with wrong user role`() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val inviter = userCommonTestHelper.buildUserWithClientAdminRole(clientOrganization = clientOrganization)
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest(
            organizationUuid = organization.uuid,
            inviterUserUuid = inviter.uuid,
            userRoleRequestModels = listOf(
                SingleUserInvitationToClientRequestModel(
                    clientOrganization.uuid,
                    UserRoleModel.ORGANIZATION_ADMIN
                )
            )
        )
        val inviterPermissions = userRoleCommonTestHelper.buildUserClientAdminRole(inviter, clientOrganization)
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(clientOrganizationService.existsByUuid(eq(clientOrganization.uuid))).andReturn(true).once()
        expect(userService.findByEmail(eq(request.email))).andReturn(
            Optional.empty()
        )
        expect(userService.getByUuid(eq(inviter.uuid))).andReturn(inviter)
        expect(
            userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(
                request.organizationUuid,
                request.inviterUserUuid
            )
        ).andReturn(listOf(inviterPermissions))
        expect(
            userRoleService.findByOrganizationAndUser(
                eq(organization.uuid),
                eq(inviter.uuid)
            )
        ).andReturn(Optional.empty())
        replayAll()
        preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INCORRECT_PERMISSIONS)
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
            userRoleRequestModels = listOf(
                SingleUserInvitationToClientRequestModel(
                    clientOrganization.uuid,
                    UserRoleModel.CLIENT_ORGANIZATION_VIEWER
                )
            )
        )
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(clientOrganizationService.existsByUuid(eq(clientOrganization.uuid))).andReturn(true).once()
        expect(userService.findByEmail(eq(request.email))).andReturn(
            Optional.empty()
        )
        expect(userService.getByUuid(eq(inviter.uuid))).andReturn(inviter)
        replayAll()
        assertThat(preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).isPresent).isFalse()
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
            userRoleRequestModels = listOf(
                SingleUserInvitationToClientRequestModel(
                    clientOrganization.uuid,
                    UserRoleModel.CLIENT_ORGANIZATION_VIEWER
                )
            )
        )
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(clientOrganizationService.existsByUuid(eq(clientOrganization.uuid))).andReturn(true).once()
        expect(userService.findByEmail(eq(request.email))).andReturn(
            Optional.empty()
        )
        expect(userService.getByUuid(eq(inviter.uuid))).andReturn(inviter)
        expect(
            userRoleService.findByOrganizationAndUser(
                eq(organization.uuid),
                eq(inviter.uuid)
            )
        ).andReturn(Optional.of(UserOrganizationOwnerRole(inviter, organization)))
        replayAll()
        assertThat(preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).isPresent).isFalse()
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
            userRoleRequestModels = listOf(
                SingleUserInvitationToClientRequestModel(
                    uuid(),
                    UserRoleModel.CLIENT_ORGANIZATION_VIEWER
                )
            )
        )
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(
            userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(
                request.organizationUuid,
                request.inviterUserUuid
            )
        ).andReturn(listOf(inviterPermissions))
        expect(clientOrganizationService.existsByUuid(request.invitations[0].clientUuid)).andReturn(true).once()
        expect(userService.findByEmail(eq(request.email))).andReturn(
            Optional.empty()
        )
        expect(userService.getByUuid(eq(inviter.uuid))).andReturn(inviter)
        expect(
            userRoleService.findByOrganizationAndUser(
                eq(organization.uuid),
                eq(inviter.uuid)
            )
        ).andReturn(Optional.empty())
        replayAll()
        preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INCORRECT_PERMISSIONS)
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
            userRoleRequestModels = listOf(
                SingleUserInvitationToClientRequestModel(
                    inviterPermissions.clientOrganization.uuid,
                    UserRoleModel.CLIENT_ORGANIZATION_VIEWER
                )
            )
        )
        resetAll()
        expect(userService.existsByUuid(request.inviterUserUuid)).andReturn(true)
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(
            userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(
                request.organizationUuid,
                request.inviterUserUuid
            )
        ).andReturn(listOf(inviterPermissions))
        expect(clientOrganizationService.existsByUuid(request.invitations[0].clientUuid)).andReturn(true).once()
        expect(userService.findByEmail(eq(request.email))).andReturn(
            Optional.empty()
        )
        expect(
            userRoleService.findByOrganizationAndUser(
                eq(organization.uuid),
                eq(inviter.uuid)
            )
        ).andReturn(Optional.empty())
        expect(userService.getByUuid(eq(inviter.uuid))).andReturn(inviter)
        expect(
            userRolesPermissionsCheckerComponent.isPermittedToInvite(
                UserRoleModel.CLIENT_ORGANIZATION_ADMIN,
                UserRoleModel.CLIENT_ORGANIZATION_VIEWER
            )
        ).andReturn(true).once()
        replayAll()
        assertThat(preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request).isPresent).isFalse()
        verifyAll()
    }
}
package com.vntana.core.rest.resource.invitation.user.impl

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.invitation.InvitationStatusModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.model.user.role.request.UserRoleGrantOrganizationAdminRequest
import com.vntana.core.rest.resource.invitation.user.AbstractInvitationUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 5:37 PM
 */
class InvitationUserAcceptWebTest : AbstractInvitationUserWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.accept(resourceTestHelper.buildAcceptInvitationUserRequest(token = null)),
                InvitationUserErrorResponseModel.MISSING_INVITATION_TOKEN
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.accept(resourceTestHelper.buildAcceptInvitationUserRequest(token = null)),
                InvitationUserErrorResponseModel.MISSING_INVITATION_TOKEN
        )
    }

    @Test
    fun `test when token not found`() {
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND,
                invitationUserResourceClient.accept(resourceTestHelper.buildAcceptInvitationUserRequest()),
                InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN
        )
    }

    @Test
    fun `test when already has any role`() {
        val token = uuid()
        val userEmail = email()
        val userUuid = userResourceTestHelper.persistUser(userResourceTestHelper.buildCreateUserRequest(email = userEmail)).response().uuid
        val newOrganizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val invitationUserUuid = resourceTestHelper.persistInvitationUser(userRole = UserRoleModel.ORGANIZATION_ADMIN, email = userEmail, organizationUuid = newOrganizationUuid)
        tokenResourceTestHelper.persistTokenInvitationUser(token = token, invitationUserUuid = invitationUserUuid)
        userRoleResourceTestHelper.grantUserOrganizationAdminRole(userUuid, newOrganizationUuid)
        val request = resourceTestHelper.buildAcceptInvitationUserRequest(token = token)
        assertBasicErrorResultResponse(HttpStatus.CONFLICT, invitationUserResourceClient.accept(request), InvitationUserErrorResponseModel.USER_ALREADY_HAS_ROLE_IN_ORGANIZATION)
    }

    @Test
    fun test() {
        val token = uuid()
        val userEmail = email()
        val userUuid = userResourceTestHelper.persistUser(userResourceTestHelper.buildCreateUserRequest(email = userEmail)).response().uuid
        val newOrganizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val invitationUserUuid = resourceTestHelper.persistInvitationUser(userRole = UserRoleModel.ORGANIZATION_ADMIN, email = userEmail, organizationUuid = newOrganizationUuid)
        tokenResourceTestHelper.persistTokenInvitationUser(token = token, invitationUserUuid = invitationUserUuid)
        val request = resourceTestHelper.buildAcceptInvitationUserRequest(token = token)
        invitationUserResourceClient.accept(request).let { responseEntity ->
            assertBasicSuccessResultResponse(responseEntity)
            responseEntity?.body?.response()?.let {
                assertThat(it.organizationUuid).isEqualTo(newOrganizationUuid)
                assertThat(it.userUuid).isEqualTo(userUuid)
                assertThat(it.userRoleModel).isEqualTo(UserRoleModel.ORGANIZATION_ADMIN)
            }
        }
        assertThat(userResourceClient.getUsersByRoleAndOrganizationUuid(UserRoleModel.ORGANIZATION_ADMIN, newOrganizationUuid)
                ?.body?.response()?.items()?.map { model -> model.email }
        ).contains(userEmail)
        val acceptedInvitations = invitationUserResourceClient.getAllByStatus(invitationUserResourceTestHelper.buildGetAllByStatusInvitationUserRequest(
                size = Int.MAX_VALUE,
                organizationUuid = newOrganizationUuid,
                invitationStatus = InvitationStatusModel.ACCEPTED
        ))?.body?.response()?.items()?.map { model -> model.uuid }?.toList()
        assertThat(acceptedInvitations).containsAnyOf(invitationUserUuid)
        assertThat(tokenResourceClient.isExpire(request.token)?.body?.response()?.expired).isTrue()
    }
}
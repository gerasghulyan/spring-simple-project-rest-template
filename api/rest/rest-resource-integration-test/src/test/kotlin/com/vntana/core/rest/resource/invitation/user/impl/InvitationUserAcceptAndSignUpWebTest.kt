package com.vntana.core.rest.resource.invitation.user.impl

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.invitation.InvitationStatusModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.resource.invitation.user.AbstractInvitationUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Arman Gevorgyan.
 * Date: 5/15/20
 * Time: 5:45 PM
 */
class InvitationUserAcceptAndSignUpWebTest : AbstractInvitationUserWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.acceptAndSignUp(resourceTestHelper.buildAcceptInvitationUserAndSignUpRequest(token = null)),
                InvitationUserErrorResponseModel.MISSING_INVITATION_TOKEN
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.acceptAndSignUp(resourceTestHelper.buildAcceptInvitationUserAndSignUpRequest(token = emptyString())),
                InvitationUserErrorResponseModel.MISSING_INVITATION_TOKEN
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.acceptAndSignUp(resourceTestHelper.buildAcceptInvitationUserAndSignUpRequest(newUserFullName = null)),
                InvitationUserErrorResponseModel.MISSING_USER_FULL_NAME
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.acceptAndSignUp(resourceTestHelper.buildAcceptInvitationUserAndSignUpRequest(newUserFullName = emptyString())),
                InvitationUserErrorResponseModel.MISSING_USER_FULL_NAME
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.acceptAndSignUp(resourceTestHelper.buildAcceptInvitationUserAndSignUpRequest(password = null)),
                InvitationUserErrorResponseModel.MISSING_PASSWORD
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.acceptAndSignUp(resourceTestHelper.buildAcceptInvitationUserAndSignUpRequest(password = emptyString())),
                InvitationUserErrorResponseModel.MISSING_PASSWORD
        )
    }

    @Test
    fun `test when token not found`() {
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND,
                invitationUserResourceClient.acceptAndSignUp(resourceTestHelper.buildAcceptInvitationUserAndSignUpRequest()),
                InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN
        )
    }

    @Test
    fun `test when token is expired`() {
        val token = uuid()
        tokenResourceTestHelper.persistTokenInvitationUser(token = token)
        tokenResourceTestHelper.expire(token)
        assertBasicErrorResultResponse(HttpStatus.NOT_ACCEPTABLE,
                invitationUserResourceClient.acceptAndSignUp(resourceTestHelper.buildAcceptInvitationUserAndSignUpRequest(token = token)),
                InvitationUserErrorResponseModel.TOKEN_IS_EXPIRED
        )
    }

    @Test
    fun test() {
        val token = uuid()
        val userEmail = email()
        val newUserFullName = uuid()
        val password = uuid()
        val newOrganizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val invitationUserUuid = resourceTestHelper.persistInvitationUser(userRole = UserRoleModel.ORGANIZATION_ADMIN, email = userEmail, organizationUuid = newOrganizationUuid)
        tokenResourceTestHelper.persistTokenInvitationUser(token = token, invitationUserUuid = invitationUserUuid)
        val request = resourceTestHelper.buildAcceptInvitationUserAndSignUpRequest(token = token, newUserFullName = newUserFullName, password = password)
        invitationUserResourceClient.acceptAndSignUp(request).let { responseEntity ->
            assertBasicSuccessResultResponse(responseEntity)
            responseEntity?.body?.response()?.let {
                assertThat(it.organizationUuid).isEqualTo(newOrganizationUuid)
                assertThat(it.userRoleModel).isEqualTo(UserRoleModel.ORGANIZATION_ADMIN)
                assertThat(it.userUuid).isNotEmpty()
                assertThat(userResourceClient.accountDetails(it.userUuid)?.body?.response()?.isEmailVerified).isTrue()
            }
        }
        userResourceClient.getUsersByRoleAndOrganizationUuid(UserRoleModel.ORGANIZATION_ADMIN, newOrganizationUuid)
                ?.body?.response()?.let {
            assertThat(it.totalCount()).isEqualTo(1)
            assertThat(it.items()[0].fullName).isEqualTo(newUserFullName)
            assertThat(it.items()[0].email).isEqualTo(userEmail)
        }
        val acceptedInvitations = invitationUserResourceClient.getAllByStatus(invitationUserResourceTestHelper.buildGetAllByStatusInvitationUserRequest(
                size = Int.MAX_VALUE,
                organizationUuid = newOrganizationUuid,
                invitationStatus = InvitationStatusModel.ACCEPTED
        ))?.body?.response()?.items()?.map { model -> model.uuid }?.toList()
        assertThat(acceptedInvitations).containsAnyOf(invitationUserUuid)
        assertThat(tokenResourceClient.isExpire(request.token)?.body?.response()?.expired).isTrue()
    }
}
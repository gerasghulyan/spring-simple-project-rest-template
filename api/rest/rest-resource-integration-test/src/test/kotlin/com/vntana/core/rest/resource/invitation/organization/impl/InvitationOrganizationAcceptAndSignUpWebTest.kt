package com.vntana.core.rest.resource.invitation.organization.impl

import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel
import com.vntana.core.model.user.request.FindUserByEmailRequest
import com.vntana.core.rest.resource.invitation.organization.AbstractInvitationOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Geras Ghulyan
 * Date: 4/9/2020
 * Time: 11:33 AM
 */
class InvitationOrganizationAcceptAndSignUpWebTest : AbstractInvitationOrganizationWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationOrganizationResourceClient.acceptAndSignUp(resourceTestHelper.buildAcceptAndSignUpInvitationOrganizationRequest(token = null)),
                InvitationOrganizationErrorResponseModel.MISSING_TOKEN
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationOrganizationResourceClient.acceptAndSignUp(resourceTestHelper.buildAcceptAndSignUpInvitationOrganizationRequest(token = emptyString())),
                InvitationOrganizationErrorResponseModel.MISSING_TOKEN
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationOrganizationResourceClient.acceptAndSignUp(resourceTestHelper.buildAcceptAndSignUpInvitationOrganizationRequest(organizationName = null)),
                InvitationOrganizationErrorResponseModel.MISSING_ORGANIZATION_NAME
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationOrganizationResourceClient.acceptAndSignUp(resourceTestHelper.buildAcceptAndSignUpInvitationOrganizationRequest(organizationName = emptyString())),
                InvitationOrganizationErrorResponseModel.MISSING_ORGANIZATION_NAME
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationOrganizationResourceClient.acceptAndSignUp(resourceTestHelper.buildAcceptAndSignUpInvitationOrganizationRequest(organizationSlug = null)),
                InvitationOrganizationErrorResponseModel.MISSING_ORGANIZATION_SLUG
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationOrganizationResourceClient.acceptAndSignUp(resourceTestHelper.buildAcceptAndSignUpInvitationOrganizationRequest(organizationSlug = emptyString())),
                InvitationOrganizationErrorResponseModel.MISSING_ORGANIZATION_SLUG
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationOrganizationResourceClient.acceptAndSignUp(resourceTestHelper.buildAcceptAndSignUpInvitationOrganizationRequest(userFullName = null)),
                InvitationOrganizationErrorResponseModel.MISSING_USER_FULL_NAME
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationOrganizationResourceClient.acceptAndSignUp(resourceTestHelper.buildAcceptAndSignUpInvitationOrganizationRequest(userFullName = emptyString())),
                InvitationOrganizationErrorResponseModel.MISSING_USER_FULL_NAME
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationOrganizationResourceClient.acceptAndSignUp(resourceTestHelper.buildAcceptAndSignUpInvitationOrganizationRequest(userPassword = null)),
                InvitationOrganizationErrorResponseModel.MISSING_USER_PASSWORD
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationOrganizationResourceClient.acceptAndSignUp(resourceTestHelper.buildAcceptAndSignUpInvitationOrganizationRequest(userPassword = emptyString())),
                InvitationOrganizationErrorResponseModel.MISSING_USER_PASSWORD
        )
    }

    @Test
    fun `test acceptAndSignUp`() {
        val email = email()
        val invitationOrganizationUuid = resourceTestHelper.persistInvitationOrganization(email = email)
        val token = uuid()
        val request = resourceTestHelper.buildAcceptAndSignUpInvitationOrganizationRequest(token = token)
        tokenResourceTestHelper.persistTokenInvitationOrganization(
                invitationOrganizationUuid = invitationOrganizationUuid,
                token = token
        )
        invitationOrganizationResourceClient.acceptAndSignUp(request).let {
            assertBasicSuccessResultResponse(it)
        }
        val userUuid = userResourceClient.findByEmail(FindUserByEmailRequest(email)).response().uuid
        userResourceClient.accountDetails(userUuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body.response().uuid).isEqualTo(userUuid)
            assertThat(it.body.response().isEmailVerified).isTrue()
        }
    }
}
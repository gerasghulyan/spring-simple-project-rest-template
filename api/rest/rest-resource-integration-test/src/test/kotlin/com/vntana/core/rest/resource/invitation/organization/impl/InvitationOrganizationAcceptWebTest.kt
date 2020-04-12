package com.vntana.core.rest.resource.invitation.organization.impl

import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel
import com.vntana.core.rest.resource.invitation.organization.AbstractInvitationOrganizationWebTest
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Geras Ghulyan
 * Date: 4/9/2020
 * Time: 11:33 AM
 */
class InvitationOrganizationAcceptWebTest : AbstractInvitationOrganizationWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationOrganizationResourceClient.accept(resourceTestHelper.buildAcceptInvitationOrganizationRequest(token = null)),
                InvitationOrganizationErrorResponseModel.MISSING_TOKEN
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationOrganizationResourceClient.accept(resourceTestHelper.buildAcceptInvitationOrganizationRequest(token = emptyString())),
                InvitationOrganizationErrorResponseModel.MISSING_TOKEN
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationOrganizationResourceClient.accept(resourceTestHelper.buildAcceptInvitationOrganizationRequest(organizationName = null)),
                InvitationOrganizationErrorResponseModel.MISSING_ORGANIZATION_NAME
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationOrganizationResourceClient.accept(resourceTestHelper.buildAcceptInvitationOrganizationRequest(organizationName = emptyString())),
                InvitationOrganizationErrorResponseModel.MISSING_ORGANIZATION_NAME
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationOrganizationResourceClient.accept(resourceTestHelper.buildAcceptInvitationOrganizationRequest(organizationSlug = null)),
                InvitationOrganizationErrorResponseModel.MISSING_ORGANIZATION_SLUG
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationOrganizationResourceClient.accept(resourceTestHelper.buildAcceptInvitationOrganizationRequest(organizationSlug = emptyString())),
                InvitationOrganizationErrorResponseModel.MISSING_ORGANIZATION_SLUG
        )
    }

    @Test
    fun `test accept`() {
        val email = email()
        userResourceClient.createUser(userResourceTestHelper.buildCreateUserRequest(email = email))
        val invitationOrganizationUuid = resourceTestHelper.persistInvitationOrganization(email = email)
        val token = uuid()
        val request = resourceTestHelper.buildAcceptInvitationOrganizationRequest(token = token)
        tokenResourceTestHelper.persistTokenInvitationOrganization(
                invitationOrganizationUuid = invitationOrganizationUuid,
                token = token
        )
        val response = invitationOrganizationResourceClient.accept(request)
        assertBasicSuccessResultResponse(response)
    }
}
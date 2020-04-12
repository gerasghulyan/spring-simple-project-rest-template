package com.vntana.core.rest.resource.invitation.organization.impl

import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel
import com.vntana.core.rest.resource.invitation.organization.AbstractInvitationOrganizationWebTest
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Manuk Gharslyan.
 * Date: 4/9/2020
 * Time: 11:33 AM
 */
class InvitationOrganizationRejectWebTest : AbstractInvitationOrganizationWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationOrganizationResourceClient.reject(resourceTestHelper.buildRejectInvitationOrganizationRequest(token = null)),
                InvitationOrganizationErrorResponseModel.MISSING_TOKEN
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationOrganizationResourceClient.reject(resourceTestHelper.buildRejectInvitationOrganizationRequest(token = emptyString())),
                InvitationOrganizationErrorResponseModel.MISSING_TOKEN
        )
    }

    @Test
    fun `test when token does not exist`() {
        assertBasicErrorResultResponse(
                HttpStatus.NOT_FOUND,
                invitationOrganizationResourceClient.reject(resourceTestHelper.buildRejectInvitationOrganizationRequest()),
                InvitationOrganizationErrorResponseModel.TOKEN_NOT_FOUND
        )
    }

    @Test
    fun `test reject`() {
        val invitationOrganizationUuid = resourceTestHelper.persistInvitationOrganization()
        val token = uuid()
        val request = resourceTestHelper.buildRejectInvitationOrganizationRequest(token = token)
        tokenResourceTestHelper.persistTokenInvitationOrganization(
                invitationOrganizationUuid = invitationOrganizationUuid,
                token = token
        )
        assertBasicSuccessResultResponse(invitationOrganizationResourceClient.reject(request))
    }
}
package com.vntana.core.rest.resource.token.impl

import com.vntana.core.model.token.error.TokenErrorResponseModel
import com.vntana.core.rest.resource.token.AbstractTokenWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 2:26 PM
 */
class TokenCreateTokenInvitationOrganizationWebTest : AbstractTokenWebTest() {

    @Test
    fun `test when token is missing`() {
        assertBasicErrorResultResponse(
                tokenResourceClient.createTokenInvitationOrganization(resourceTestHelper.buildCreateTokenInvitationOrganizationRequest(token = null)),
                TokenErrorResponseModel.MISSING_TOKEN
        )
        assertBasicErrorResultResponse(
                tokenResourceClient.createTokenInvitationOrganization(resourceTestHelper.buildCreateTokenInvitationOrganizationRequest(token = emptyString())),
                TokenErrorResponseModel.MISSING_TOKEN
        )
        assertBasicErrorResultResponse(
                tokenResourceClient.createTokenInvitationOrganization(resourceTestHelper.buildCreateTokenInvitationOrganizationRequest(invitationOrganizationUuid = null)),
                TokenErrorResponseModel.MISSING_INVITATION_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(
                tokenResourceClient.createTokenInvitationOrganization(resourceTestHelper.buildCreateTokenInvitationOrganizationRequest(invitationOrganizationUuid = emptyString())),
                TokenErrorResponseModel.MISSING_INVITATION_ORGANIZATION_UUID
        )
    }

    @Test
    fun `test when invitation organization not found`() {
        val request = resourceTestHelper.buildCreateTokenInvitationOrganizationRequest()
        assertBasicErrorResultResponse(tokenResourceClient.createTokenInvitationOrganization(request),
                TokenErrorResponseModel.INVITATION_ORGANIZATION_NOT_FOUND
        )
    }

    @Test
    fun test() {
        val invitationOrganizationUuid = invitationOrganizationResourceTestHelper.persistInvitationOrganization()
        val request = resourceTestHelper.buildCreateTokenInvitationOrganizationRequest(
                invitationOrganizationUuid = invitationOrganizationUuid
        )
        tokenResourceClient.createTokenInvitationOrganization(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it?.body?.response()?.uuid).isNotBlank()
        }
    }
}
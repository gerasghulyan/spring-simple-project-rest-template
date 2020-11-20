package com.vntana.core.rest.resource.token.impl

import com.vntana.core.model.token.error.TokenErrorResponseModel
import com.vntana.core.rest.resource.token.AbstractTokenWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 3:33 PM
 */
class TokenCreateTokenInvitationUserToOrganizationWebTest : AbstractTokenWebTest() {

    @Test
    fun `test when token is missing`() {
        assertBasicErrorResultResponse(
                tokenResourceClient.createTokenInvitationUserToOrganization(resourceTestHelper.buildCreateTokenInvitationUserRequest(token = null)),
                TokenErrorResponseModel.MISSING_TOKEN
        )
        assertBasicErrorResultResponse(
                tokenResourceClient.createTokenInvitationUserToOrganization(resourceTestHelper.buildCreateTokenInvitationUserRequest(token = emptyString())),
                TokenErrorResponseModel.MISSING_TOKEN
        )
        assertBasicErrorResultResponse(
                tokenResourceClient.createTokenInvitationUserToOrganization(resourceTestHelper.buildCreateTokenInvitationUserRequest(invitationUserUuid = null)),
                TokenErrorResponseModel.MISSING_INVITATION_USER_UUID
        )
        assertBasicErrorResultResponse(
                tokenResourceClient.createTokenInvitationUserToOrganization(resourceTestHelper.buildCreateTokenInvitationUserRequest(invitationUserUuid = emptyString())),
                TokenErrorResponseModel.MISSING_INVITATION_USER_UUID
        )
    }

    @Test
    fun `test when invitation organization not found`() {
        val request = resourceTestHelper.buildCreateTokenInvitationUserRequest()
        assertBasicErrorResultResponse(tokenResourceClient.createTokenInvitationUserToOrganization(request),
                TokenErrorResponseModel.INVITATION_USER_NOT_FOUND
        )
    }

    @Test
    fun test() {
        val invitationUserUuid = invitationUserResourceTestHelper.persistInvitationUserToOrganization()
        val request = resourceTestHelper.buildCreateTokenInvitationUserRequest(
                invitationUserUuid = invitationUserUuid
        )
        tokenResourceClient.createTokenInvitationUserToOrganization(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it?.body?.response()?.uuid).isNotBlank()
        }
    }
}
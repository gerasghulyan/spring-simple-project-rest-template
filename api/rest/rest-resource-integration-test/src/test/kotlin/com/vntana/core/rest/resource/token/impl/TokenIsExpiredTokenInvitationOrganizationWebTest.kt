package com.vntana.core.rest.resource.token.impl

import com.vntana.core.model.token.error.TokenErrorResponseModel
import com.vntana.core.rest.resource.token.AbstractTokenWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Manuk Gharslyan.
 * Date: 4/8/2020
 * Time: 11:34 AM
 */
class TokenIsExpiredTokenInvitationOrganizationWebTest : AbstractTokenWebTest() {

    @Test
    fun `test when token not found`() {
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND, tokenResourceClient.isExpire(uuid()), TokenErrorResponseModel.TOKEN_NOT_FOUND)
    }

    @Test
    fun `test is expired`() {
        val invitationOrganizationUuid = invitationOrganizationResourceTestHelper.persistInvitationOrganization()
        val token = uuid()
        tokenResourceTestHelper.persistTokenInvitationOrganization(request = tokenResourceTestHelper.buildCreateTokenInvitationOrganizationRequest(
                invitationOrganizationUuid = invitationOrganizationUuid,
                token = token
        ))
        tokenResourceTestHelper.expire(token)
        tokenResourceClient.isExpire(token).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body?.response()?.expired).isTrue()
        }
    }
}
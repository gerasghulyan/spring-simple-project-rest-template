package com.vntana.core.rest.facade.token.impl

import com.vntana.commons.api.model.response.ErrorResponseModel
import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.token.error.TokenErrorResponseModel
import com.vntana.core.rest.facade.token.AbstractTokenFacadePreconditionCheckerUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 12:21 PM
 */
class TokeFacadePreconditionCheckerCheckCreateTokenInvitationOrganizationUnitTest : AbstractTokenFacadePreconditionCheckerUnitTest() {

    @Test
    fun `test when invitation organization not found`() {
        resetAll()
        val request = restTestHelper.buildCreateTokenInvitationOrganizationRequest()
        expect(invitationOrganizationService.existsByUuid(request.invitationOrganizationUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkCreateTokenInvitationOrganization(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(TokenErrorResponseModel.INVITATION_ORGANIZATION_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildCreateTokenInvitationOrganizationRequest()
        expect(invitationOrganizationService.existsByUuid(request.invitationOrganizationUuid)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkCreateTokenInvitationOrganization(request)).isEqualTo(SingleErrorWithStatus.empty<ErrorResponseModel>())
        verifyAll()
    }
}
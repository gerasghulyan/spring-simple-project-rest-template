package com.vntana.core.rest.facade.token.component.impl

import com.vntana.commons.api.model.response.ErrorResponseModel
import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.token.error.TokenErrorResponseModel
import com.vntana.core.rest.facade.token.component.AbstractTokenFacadePreconditionCheckerUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 3:21 PM
 */
class TokeFacadePreconditionCheckerCheckCreateTokenInvitationUserUnitTest : AbstractTokenFacadePreconditionCheckerUnitTest() {

    @Test
    fun `test when invitation user not found`() {
        resetAll()
        val request = restTestHelper.buildCreateTokenInvitationUserRequest()
        expect(invitationUserToOrganizationService.existsByUuid(request.userInvitationUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkCreateTokenUserInvitationToOrganization(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(TokenErrorResponseModel.INVITATION_USER_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildCreateTokenInvitationUserRequest()
        expect(invitationUserToOrganizationService.existsByUuid(request.userInvitationUuid)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkCreateTokenUserInvitationToOrganization(request)).isEqualTo(SingleErrorWithStatus.empty<ErrorResponseModel>())
        verifyAll()
    }
}
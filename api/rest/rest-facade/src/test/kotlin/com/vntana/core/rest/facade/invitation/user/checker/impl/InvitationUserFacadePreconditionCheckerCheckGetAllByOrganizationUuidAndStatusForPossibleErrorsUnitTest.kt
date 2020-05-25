package com.vntana.core.rest.facade.invitation.user.checker.impl

import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.facade.invitation.user.checker.AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/18/2020
 * Time: 12:44 PM
 */
class InvitationUserFacadePreconditionCheckerCheckGetAllByOrganizationUuidAndStatusForPossibleErrorsUnitTest : AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest() {

    @Test
    fun `test when organization does not exist`() {
        val request = invitationUserRestTestHelper.buildGetAllByStatusInvitationUserRequest()
        resetAll()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkGetAllByOrganizationUuidAndStatusForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVITING_ORGANIZATION_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun test() {
        val request = invitationUserRestTestHelper.buildGetAllByStatusInvitationUserRequest()
        resetAll()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkGetAllByOrganizationUuidAndStatusForPossibleErrors(request).isPresent).isFalse()
        verifyAll()
    }
}
package com.vntana.core.rest.facade.invitation.organization.checker.impl

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel
import com.vntana.core.rest.facade.invitation.organization.checker.AbstractInvitationOrganizationFacadePreconditionCheckerFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan
 * Date: 4/9/2020
 * Time: 2:52 PM
 */
class InvitationOrganizationFacadePreconditionCheckerCheckAcceptWithUserForPossibleErrorsUnitTest : AbstractInvitationOrganizationFacadePreconditionCheckerFacadeUnitTest() {

    @Test
    fun `test check when USER_NOT_FOUND`() {
        val tokenInvitationOrganization = tokenInvitationOrganizationCommonTestHelper.buildTokenInvitationOrganization()
        val invitationOrganization = tokenInvitationOrganization.invitationOrganization
        resetAll()
        expect(userService.findByEmail(invitationOrganization.email)).andReturn(Optional.empty())
        replayAll()
        preconditionChecker.checkAcceptInvitationWhenUserExistsForPossibleErrors(invitationOrganization.email).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationOrganizationErrorResponseModel.USER_NOT_FOUND)
        }
        verifyAll()
    }
}
package com.vntana.core.rest.facade.invitation.organization.checker.impl

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.domain.user.User
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel
import com.vntana.core.rest.facade.invitation.organization.checker.AbstractInvitationOrganizationFacadePreconditionCheckerFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.easymock.EasyMock.isA
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan
 * Date: 4/9/2020
 * Time: 2:52 PM
 */
class InvitationOrganizationFacadePreconditionCheckerCheckAcceptWithoutUserForPossibleErrorsUnitTest : AbstractInvitationOrganizationFacadePreconditionCheckerFacadeUnitTest() {

    @Test
    fun `test check when USER_NOT_FOUND`() {
        val tokenInvitationOrganization = tokenInvitationOrganizationCommonTestHelper.buildTokenInvitationOrganization()
        val invitationOrganization = tokenInvitationOrganization.invitationOrganization
        val user = userCommonTestHelper.buildUser()
        resetAll()
        expect(userService.findByEmail(invitationOrganization.email)).andReturn(Optional.of(user))
        replayAll()
        preconditionChecker.checkAcceptInvitationWhenUserNotExistsForPossibleErrors(invitationOrganization.email).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationOrganizationErrorResponseModel.USER_ALREADY_EXISTS_FOR_EMAIL)
        }
        verifyAll()
    }
}
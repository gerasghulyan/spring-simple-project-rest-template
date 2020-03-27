package com.vntana.core.rest.facade.invitation.organization.impl

import com.vntana.commons.api.model.response.ErrorResponseModel
import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.domain.organization.Organization
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel
import com.vntana.core.rest.facade.invitation.organization.AbstractInvitationOrganizationFacadePreconditionCheckerFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 3:07 PM
 */
class InvitationOrganizationFacadePreconditionCheckerCheckCreateForPossibleErrorsUnitTest : AbstractInvitationOrganizationFacadePreconditionCheckerFacadeUnitTest() {

    @Test
    fun `test with invalid invalid`() {
        resetAll()
        val slug = uuid()
        val request = restTestHelper.buildCreateInvitationOrganizationRequest(slug = slug)
        expect(slugValidationComponent.validate(slug)).andReturn(false)
        replayAll()
        preconditionChecker.checkCreateForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_PRECONDITION_FAILED)
            assertThat(it.error).isEqualTo(InvitationOrganizationErrorResponseModel.INVALID_SLUG)
        }
        verifyAll()
    }

    @Test
    fun `test when slug is not available`() {
        resetAll()
        val slug = uuid()
        val request = restTestHelper.buildCreateInvitationOrganizationRequest(slug = slug)
        expect(slugValidationComponent.validate(slug)).andReturn(true)
        expect(organizationService.findBySlug(slug)).andReturn(Optional.of(Organization()))
        replayAll()
        preconditionChecker.checkCreateForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationOrganizationErrorResponseModel.SLUG_IS_NOT_AVAILABLE)

        }
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val slug = uuid()
        val request = restTestHelper.buildCreateInvitationOrganizationRequest(slug = slug)
        expect(slugValidationComponent.validate(slug)).andReturn(true)
        expect(organizationService.findBySlug(slug)).andReturn(Optional.empty())
        replayAll()
        assertThat(preconditionChecker.checkCreateForPossibleErrors(request)).isEqualTo(SingleErrorWithStatus.empty<ErrorResponseModel>())
        verifyAll()
    }
}
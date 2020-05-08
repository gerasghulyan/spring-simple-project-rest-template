package com.vntana.core.rest.facade.invitation.organization.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.domain.user.UserRole
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel
import com.vntana.core.persistence.utils.Executable
import com.vntana.core.rest.facade.invitation.organization.AbstractInvitationOrganizationFacadeUnitTest
import com.vntana.core.service.invitation.organization.dto.UpdateInvitationOrganizationStatusDto
import com.vntana.core.service.user.dto.UserGrantOrganizationRoleDto
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Geras Ghulyan
 * Date: 4/9/2020
 * Time: 11:21 AM
 */
class InvitationOrganizationAcceptFacadeUnitTest : AbstractInvitationOrganizationFacadeUnitTest() {

    @Test
    fun `test when precondition check failed`() {
        val request = restTestHelper.buildAcceptInvitationOrganizationRequest()
        resetAll()
        expect(preconditionChecker.checkAcceptInvitationForPossibleErrors(request))
                .andReturn(SingleErrorWithStatus.of(404, InvitationOrganizationErrorResponseModel.NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(invitationOrganizationServiceFacade.accept(request), InvitationOrganizationErrorResponseModel.NOT_FOUND)
        verifyAll()
    }

    @Test
    fun `test when precondition check for user failed`() {
        val request = restTestHelper.buildAcceptInvitationOrganizationRequest()
        val tokenInvitationOrganization = tokenInvitationOrganizationCommonTestHelper.buildTokenInvitationOrganization()
        val invitationOrganization = tokenInvitationOrganization.invitationOrganization
        resetAll()
        expect(preconditionChecker.checkAcceptInvitationForPossibleErrors(request)).andReturn(SingleErrorWithStatus.empty())
        expect(tokenInvitationOrganizationService.getByToken(request.getToken())).andReturn(tokenInvitationOrganization)
        expect(preconditionChecker.checkAcceptInvitationWhenUserExistsForPossibleErrors(invitationOrganization.email))
                .andReturn(SingleErrorWithStatus.of(404, InvitationOrganizationErrorResponseModel.NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(invitationOrganizationServiceFacade.accept(request), InvitationOrganizationErrorResponseModel.NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        val request = restTestHelper.buildAcceptInvitationOrganizationRequest()
        val tokenInvitationOrganization = tokenInvitationOrganizationCommonTestHelper.buildTokenInvitationOrganization()
        val invitationOrganization = tokenInvitationOrganization.invitationOrganization
        val user = userCommonTestHelper.buildUser()
        val organization = organizationCommonTestHelper.buildOrganization()
        val dto = organizationCommonTestHelper.buildCreateOrganizationFromInvitationDto(request.organizationName, request.organizationSlug, invitationOrganization.uuid)
        resetAll()
        expect(preconditionChecker.checkAcceptInvitationForPossibleErrors(request)).andReturn(SingleErrorWithStatus.empty())
        expect(tokenInvitationOrganizationService.getByToken(request.getToken())).andReturn(tokenInvitationOrganization)
        expect(preconditionChecker.checkAcceptInvitationWhenUserExistsForPossibleErrors(invitationOrganization.email)).andReturn(SingleErrorWithStatus.empty())
        expect(userService.getByEmail(invitationOrganization.email)).andReturn(user)
        expect(persistenceUtilityService.runInNewTransaction(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(organizationService.createWithInvitation(dto)).andReturn(organization)
        expect(organizationLifecycleMediator.onCreated(organization))
        expect(organizationUuidAwareLifecycleMediator.onCreated(organization.uuid)).andVoid()
        expect(userService.grantOrganizationRole(UserGrantOrganizationRoleDto(
                user.uuid,
                organization.uuid,
                UserRole.ORGANIZATION_OWNER)
        )).andVoid()
        expect(tokenService.findByTokenAndExpire(request.token)).andReturn(tokenInvitationOrganization)
        expect(invitationOrganizationService.updateStatus(UpdateInvitationOrganizationStatusDto(
                invitationOrganization.uuid,
                InvitationStatus.ACCEPTED
        ))).andReturn(invitationOrganization)
        expect(invitationOrganizationUuidAwareLifecycleMediator.onUpdated(invitationOrganization.uuid)).andVoid()
        replayAll()
        assertBasicSuccessResultResponse(invitationOrganizationServiceFacade.accept(request))
        verifyAll()
    }
}
package com.vntana.core.rest.facade.invitation.organization.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.domain.user.UserRole
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel
import com.vntana.core.model.invitation.organization.request.AcceptInvitationOrganizationRequest
import com.vntana.core.persistence.utils.Executable
import com.vntana.core.rest.facade.invitation.organization.AbstractInvitationOrganizationFacadeUnitTest
import com.vntana.core.service.invitation.organization.dto.AcceptInvitationOrganizationDto
import com.vntana.core.service.invitation.organization.dto.UpdateInvitationOrganizationStatusDto
import com.vntana.core.service.organization.dto.CreateOrganizationDto
import com.vntana.core.service.user.dto.CreateUserDto
import com.vntana.core.service.user.dto.UserGrantOrganizationRoleDto
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.junit.Ignore
import org.junit.Test

/**
 * Created by Geras Ghulyan
 * Date: 4/9/2020
 * Time: 11:21 AM
 */
class InvitationOrganizationAcceptAndSignUpFacadeUnitTest : AbstractInvitationOrganizationFacadeUnitTest() {

    @Test
    fun `test when precondition check failed`() {
        val request = restTestHelper.buildAcceptAndSignUpInvitationOrganizationRequest()
        val acceptRequest = restTestHelper.buildAcceptInvitationOrganizationRequest(
                token = request.token,
                organizationName = request.organizationName,
                organizationSlug = request.organizationSlug
        )
        resetAll()
        expect(preconditionChecker.checkAcceptInvitationForPossibleErrors(acceptRequest))
                .andReturn(SingleErrorWithStatus.of(404, InvitationOrganizationErrorResponseModel.NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(invitationOrganizationServiceFacade.acceptAndSignUp(request), InvitationOrganizationErrorResponseModel.NOT_FOUND)
        verifyAll()
    }

    @Test
    fun `test when precondition check for user failed`() {
        val request = restTestHelper.buildAcceptAndSignUpInvitationOrganizationRequest()
        val acceptRequest = restTestHelper.buildAcceptInvitationOrganizationRequest(
                token = request.token,
                organizationName = request.organizationName,
                organizationSlug = request.organizationSlug
        )
        val tokenInvitationOrganization = tokenInvitationOrganizationCommonTestHelper.buildTokenInvitationOrganization()
        val invitationOrganization = tokenInvitationOrganization.invitationOrganization
        resetAll()
        expect(preconditionChecker.checkAcceptInvitationForPossibleErrors(acceptRequest)).andReturn(SingleErrorWithStatus.empty())
        expect(tokenInvitationOrganizationService.getByToken(request.getToken())).andReturn(tokenInvitationOrganization)
        expect(preconditionChecker.checkAcceptInvitationWhenUserNotExistsForPossibleErrors(invitationOrganization.email))
                .andReturn(SingleErrorWithStatus.of(409, InvitationOrganizationErrorResponseModel.USER_ALREADY_EXISTS_FOR_EMAIL))
        replayAll()
        assertBasicErrorResultResponse(invitationOrganizationServiceFacade.acceptAndSignUp(request), InvitationOrganizationErrorResponseModel.USER_ALREADY_EXISTS_FOR_EMAIL)
        verifyAll()
    }

    @Test
    fun test() {
        val request = restTestHelper.buildAcceptAndSignUpInvitationOrganizationRequest()
        val acceptRequest = restTestHelper.buildAcceptInvitationOrganizationRequest(
                token = request.token,
                organizationName = request.organizationName,
                organizationSlug = request.organizationSlug
        )
        val tokenInvitationOrganization = tokenInvitationOrganizationCommonTestHelper.buildTokenInvitationOrganization()
        val invitationOrganization = tokenInvitationOrganization.invitationOrganization
        val user = userCommonTestHelper.buildUser()
        val organization = organizationCommonTestHelper.buildOrganization()
        resetAll()
        expect(preconditionChecker.checkAcceptInvitationForPossibleErrors(acceptRequest)).andReturn(SingleErrorWithStatus.empty())
        expect(tokenInvitationOrganizationService.getByToken(request.getToken())).andReturn(tokenInvitationOrganization)
        expect(preconditionChecker.checkAcceptInvitationWhenUserNotExistsForPossibleErrors(invitationOrganization.email)).andReturn(SingleErrorWithStatus.empty())
        expect(persistenceUtilityService.runInNewTransaction(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(organizationService.create(CreateOrganizationDto(
                request.organizationName,
                request.organizationSlug)
        )).andReturn(organization)
        expect(organizationLifecycleMediator.onCreated(organization))
        expect(organizationUuidAwareLifecycleMediator.onCreated(organization.uuid)).andVoid()
        expect(userService.create(CreateUserDto(
                request.userFullName,
                invitationOrganization.email,
                request.userPassword,
                organization.uuid,
                UserRole.ORGANIZATION_ADMIN)
        )).andReturn(user)
        expect(tokenService.findByTokenAndExpire(request.token)).andReturn(tokenInvitationOrganization)
        expect(invitationOrganizationService.accept(AcceptInvitationOrganizationDto(
                invitationOrganization.uuid,
                organization.uuid
        ))).andReturn(invitationOrganization)
        expect(invitationOrganizationUuidAwareLifecycleMediator.onUpdated(invitationOrganization.uuid)).andVoid()
        replayAll()
        assertBasicSuccessResultResponse(invitationOrganizationServiceFacade.acceptAndSignUp(request))
        verifyAll()
    }
}
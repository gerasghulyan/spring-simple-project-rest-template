package com.vntana.core.rest.facade.invitation.user.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.model.invitation.user.request.SingleUserInvitationToClientModel
import com.vntana.core.rest.facade.invitation.user.AbstractInvitationUserFacadeUnitTest
import com.vntana.core.service.invitation.user.dto.CreateInvitationForClientsUserDto
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 11/11/20
 * Time: 9:15 AM
 */
class InvitationUserForClientCreateFacadeUnitTest : AbstractInvitationUserFacadeUnitTest() {

    @Test
    fun `test when precondition check failed`() {
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest()
        resetAll()
        expect(preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request))
                .andReturn(SingleErrorWithStatus.of(404, InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(invitationUserServiceFacade.createInvitationForClient(request), InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val clientOrganization = organizationClientCommonTestHelper.buildClientOrganization(organization = organization)
        val request = invitationUserRestTestHelper.buildCreateInvitationUserForClientRequest(
                organizationUuid = organization.uuid,
                userRoleModels = listOf(SingleUserInvitationToClientModel(clientOrganization.uuid, UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER))
        )
        val dto = invitationUserCommonTestHelper.buildCreateInvitationUserForClientsWithContentManagerRoleDto(
                clientUuid = clientOrganization.uuid,
                email = request.email,
                inviterUserUuid = request.inviterUserUuid,
                organizationUuid = request.organizationUuid
        )
        val invitationOrganizationClientUser = listOf(
                invitationUserCommonTestHelper.buildInvitationOrganizationClientUser(
                        email = request.email, 
                        clientOrganization = clientOrganization)
        )
        resetAll()
        expect(preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request)).andReturn(SingleErrorWithStatus.empty())
        expect(mapperFacade.map(request, CreateInvitationForClientsUserDto::class.java)).andReturn(dto)
        expect(invitationUserToClientService.create(dto)).andReturn(invitationOrganizationClientUser)
        replayAll()
        assertBasicSuccessResultResponse(invitationUserServiceFacade.createInvitationForClient(request))
        verifyAll()
    }
}
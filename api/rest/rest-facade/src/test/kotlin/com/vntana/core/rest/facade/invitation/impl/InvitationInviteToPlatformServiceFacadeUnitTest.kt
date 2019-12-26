package com.vntana.core.rest.facade.invitation.impl

import com.vntana.core.model.invitation.error.InvitationErrorResponseModel
import com.vntana.core.rest.facade.invitation.AbstractInvitationServiceFacadeUnitTest
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 11:17 AM
 */
class InvitationInviteToPlatformServiceFacadeUnitTest : AbstractInvitationServiceFacadeUnitTest() {

    @Test
    fun `test when user already exists`() {
        val request = restTestHelper.buildInvitationToPlatformRequest()
        val user = userCommonTestHelper.buildUser()
        resetAll()
        expect(userService.findByEmail(request.email)).andReturn(Optional.of(user))
        replayAll()
        assertBasicErrorResultResponse(invitationServiceFacade.inviteToPlatform(request),
                InvitationErrorResponseModel.USER_ALREADY_EXISTS
        )
        verifyAll()
    }

    @Test
    fun test() {
        val request = restTestHelper.buildInvitationToPlatformRequest()
        resetAll()
        expect(userService.findByEmail(request.email)).andReturn(Optional.empty())
        expect(platformInvitationSenderComponent.sendInvitation(request.email, request.token)).andVoid()
        replayAll()
        assertBasicSuccessResultResponse(invitationServiceFacade.inviteToPlatform(request))
        verifyAll()
    }
}
package com.vntana.core.rest.facade.invitation.organization.impl

import com.vntana.core.model.invitation.organization.response.SendInvitationOrganizationResponse
import com.vntana.core.rest.facade.invitation.organization.AbstractInvitationOrganizationFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 5:52 PM
 */
class InvitationOrganizationSendInvitationFacadeUnitTest : AbstractInvitationOrganizationFacadeUnitTest() {

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildSendInvitationOrganizationRequest()
        val response = SendInvitationOrganizationResponse()
        expect(invitationOrganizationSenderComponent.sendInvitation(request)).andReturn(response)
        replayAll()
        assertThat(invitationOrganizationServiceFacade.sendInvitation(request)).isEqualTo(response)
        verifyAll()
    }
}